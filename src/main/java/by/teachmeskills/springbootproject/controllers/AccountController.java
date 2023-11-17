package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.services.OrderService;
import by.teachmeskills.springbootproject.services.UserService;
import by.teachmeskills.springbootproject.utils.UserPrincipalUtil;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static by.teachmeskills.springbootproject.ShopConstants.FILE;
import static by.teachmeskills.springbootproject.ShopConstants.USER;

@RestController
@RequestMapping("/account")
@SessionAttributes(USER)
@AllArgsConstructor
public class AccountController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public ModelAndView openAccountPage(@RequestParam(defaultValue = "0") int pageNumber,
                                        @RequestParam(defaultValue = "1") int pageSize) throws AuthorizationException {
        User user = userService.findByEmail(UserPrincipalUtil.getEmail());
        return orderService.findUserOrders(user, pageNumber, pageSize);
    }

    @GetMapping("/download")
    public void downloadOrdersToFile(HttpServletResponse response)
            throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        User user = userService.findByEmail(UserPrincipalUtil.getEmail());
        orderService.downloadOrdersToFile(response, user);
    }

    @PostMapping("/upload")
    public ModelAndView uploadOrdersFromFile(@RequestParam(FILE) MultipartFile file) throws IOException {
        return orderService.uploadOrdersFromFile(file);
    }
}
