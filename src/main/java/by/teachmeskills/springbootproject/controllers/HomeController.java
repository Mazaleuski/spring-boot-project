package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.CATEGORIES;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.HOME_PAGE;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {
    private final CategoryService categoryService;

    @GetMapping
    public ModelAndView openHomePage() {
        ModelAndView modelAndView = new ModelAndView(HOME_PAGE.getPath());
        return modelAndView.addObject(CATEGORIES, categoryService.read());
    }
}
