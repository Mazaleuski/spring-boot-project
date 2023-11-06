package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.csv.OrderCsv;
import by.teachmeskills.springbootproject.csv.converters.OrderConverter;
import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.exceptions.CartIsEmptyException;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.springbootproject.ShopConstants.ORDERS;
import static by.teachmeskills.springbootproject.ShopConstants.USER;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.ACCOUNT_PAGE;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.CART_PAGE;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    public ModelAndView create(User user, Cart cart) throws CartIsEmptyException, AuthorizationException {
        if (Optional.ofNullable(user).isEmpty()) {
            throw new AuthorizationException("Пользователь не авторизован!");
        }
        if (cart.getTotalPrice() == 0) {
            throw new CartIsEmptyException("Корзина пуста!");
        }
        Order order = Order.builder().price(cart.getTotalPrice()).date(LocalDate.now())
                .user(user).productList(cart.getProducts()).build();
        userRepository.update(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()));
        orderRepository.create(order);
        cart.clear();
        cart.setTotalPrice(0);
        ModelAndView modelAndView = new ModelAndView(CART_PAGE.getPath());
        modelAndView.addObject("info", "Заказ оформлен.");
        return modelAndView;
    }

    public ModelAndView findUserOrders(User user) throws AuthorizationException {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        if (Optional.ofNullable(user).isPresent()
                && Optional.ofNullable(user.getEmail()).isPresent()
                && Optional.ofNullable(user.getPassword()).isPresent()) {
            User loggedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
            if (Optional.ofNullable(loggedUser).isPresent()) {
                List<Order> orders = orderRepository.findByUser(loggedUser);
                modelMap.addAttribute(USER, loggedUser);
                modelMap.addAttribute(ORDERS, orders);
                modelAndView.addAllObjects(modelMap);
            }
        } else {
            throw new AuthorizationException("Пользователь не авторизован!");
        }
        modelAndView.setViewName(ACCOUNT_PAGE.getPath());
        return modelAndView;
    }

    public void downloadOrdersToFile(HttpServletResponse response, User user)
            throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (Writer writer = new OutputStreamWriter(response.getOutputStream())) {
            StatefulBeanToCsv<OrderCsv> beanToCsv = new StatefulBeanToCsvBuilder<OrderCsv>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(',')
                    .build();
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + String.format("UserId %d - orders.csv", user.getId()));
            beanToCsv.write(orderRepository.findByUser(user).stream().map(orderConverter::toCsv));
        }
    }

    public ModelAndView uploadOrdersFromFile(MultipartFile file) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/" + ACCOUNT_PAGE.getPath());
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<OrderCsv> csvToBean = new CsvToBeanBuilder<OrderCsv>(reader)
                    .withType(OrderCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreQuotations(true)
                    .withSeparator(',')
                    .build();
            List<OrderCsv> ordersCsv = new ArrayList<>();
            csvToBean.forEach(ordersCsv::add);
            ordersCsv.stream().map(orderConverter::fromCsv).forEach(orderRepository::create);
        }
        return modelAndView;
    }
}
