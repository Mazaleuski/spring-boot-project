package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.CATEGORIES;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.SEARCH_PAGE;

@RestController
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public ModelAndView openSearchPage() {
        ModelMap model = new ModelMap();
        model.addAttribute(CATEGORIES, categoryService.read());
        return new ModelAndView(SEARCH_PAGE.getPath(), model);
    }

    @PostMapping
    public ModelAndView search(String searchString) {
        return productService.findByNameOrDescription(searchString);
    }
}
