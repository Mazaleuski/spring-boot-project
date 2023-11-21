package by.teachmeskills.springbootproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.USER;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.LOGIN_PAGE;

@RestController
@RequestMapping("/login")
@SessionAttributes(USER)
@AllArgsConstructor
public class AuthController {

    @GetMapping
    public ModelAndView openLoginPage() {
        return new ModelAndView(LOGIN_PAGE.getPath());
    }

}
