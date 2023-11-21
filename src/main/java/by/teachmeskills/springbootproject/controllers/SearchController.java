package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.SearchParams;
import by.teachmeskills.springbootproject.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.SEARCH_PARAMS;

@RestController
@RequestMapping("/search")
@AllArgsConstructor
@SessionAttributes(SEARCH_PARAMS)
public class SearchController {

    private final ProductService productService;

    @GetMapping("/paging")
    public ModelAndView search(@ModelAttribute(SEARCH_PARAMS) SearchParams searchParams,
                               @RequestParam(defaultValue = "0") int pageNumber,
                               @RequestParam(defaultValue = "1") int pageSize,
                               @RequestParam(defaultValue = "name") String param) {
        return productService.findBySearchParams(searchParams, pageNumber, pageSize, param);
    }

    @ModelAttribute(SEARCH_PARAMS)
    public SearchParams searchParams() {
        return new SearchParams();
    }
}


