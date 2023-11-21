package by.teachmeskills.springbootproject.csv.converters;

import by.teachmeskills.springbootproject.csv.OrderCsv;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderConverter {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Order fromCsv(OrderCsv orderCsv) {
        String[] names = orderCsv.getProductsName().split(" ");
        List<String> listNames = new ArrayList<>();
        for (String s : names) {
            s = s.replace("*", " ");
            listNames.add(s.trim());
        }
        return Optional.of(orderCsv).map(o -> Order.builder()
                        .price(orderCsv.getPrice())
                        .date(orderCsv.getDate())
                        .user(userRepository.findById(orderCsv.getUserId()).orElse(null))
                        .productList(listNames.stream().map(productRepository::findByName).toList())
                        .build())
                .orElse(null);
    }

    public OrderCsv toCsv(Order order) {
        List<String> listNames = order.getProductList().stream().map(Product::getName).toList();
        StringBuilder sb = new StringBuilder();
        for (String s : listNames) {
            s = s.replace(" ", "*");
            sb.append(s).append(" ");
        }
        String names = sb.toString().trim();
        return Optional.of(order).map(o -> OrderCsv.builder()
                        .id(order.getId())
                        .price(order.getPrice())
                        .date(order.getDate())
                        .userId(order.getUser().getId())
                        .productsName(names)
                        .build())
                .orElse(null);
    }
}
