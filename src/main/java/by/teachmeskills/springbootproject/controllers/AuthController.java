package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.EMAIL;
import static by.teachmeskills.springbootproject.ShopConstants.PASSWORD;
import static by.teachmeskills.springbootproject.ShopConstants.USER;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.LOGIN_PAGE;
import static by.teachmeskills.springbootproject.utils.ErrorUtil.populateError;

@RestController
@RequestMapping("/login")
@SessionAttributes(USER)
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping
    public ModelAndView openLoginPage() {
        return new ModelAndView(LOGIN_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView login(@Valid @ModelAttribute(USER) User user, BindingResult bindingResult, ModelAndView modelAndView) throws AuthorizationException {
        if (bindingResult.hasErrors()) {
            populateError(EMAIL, modelAndView, bindingResult);
            populateError(PASSWORD, modelAndView, bindingResult);
            modelAndView.setViewName(LOGIN_PAGE.getPath());
            return modelAndView;
        } else {
            return userService.authenticate(user);
        }
    }

    @ModelAttribute(USER)
    public User setUpUserForm() {
        return new User();
    }
}
