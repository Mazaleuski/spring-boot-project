package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.SearchCriteria;
import by.teachmeskills.springbootproject.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.SEARCH_CRITERIA;

@RestController
@RequestMapping("/search")
@AllArgsConstructor
@SessionAttributes(SEARCH_CRITERIA)
public class SearchController {

    private final ProductService productService;

    @GetMapping
    public ModelAndView openSearchPage(@ModelAttribute(SEARCH_CRITERIA) SearchCriteria searchCriteria) {
        return productService.findByNameOrDescription(searchCriteria);
    }

    @PostMapping
    public ModelAndView search(@RequestParam String searchString, @ModelAttribute(SEARCH_CRITERIA) SearchCriteria searchCriteria) {
        searchCriteria.setSearchString(searchString);
        return productService.findByNameOrDescription(searchCriteria);
    }

    @GetMapping("next")
    public ModelAndView openNextPage(@ModelAttribute(SEARCH_CRITERIA) SearchCriteria searchCriteria) {
        searchCriteria.setPaginationNumber(searchCriteria.getPaginationNumber() + 1);
        return productService.findByNameOrDescription(searchCriteria);
    }

    @GetMapping("previous")
    public ModelAndView openPreviousPage(@ModelAttribute(SEARCH_CRITERIA) SearchCriteria searchCriteria) {
        searchCriteria.setPaginationNumber(searchCriteria.getPaginationNumber() - 1);
        return productService.findByNameOrDescription(searchCriteria);
    }

    @GetMapping("{pageNumber}")
    public ModelAndView openPageNumber(@PathVariable int pageNumber, @ModelAttribute(SEARCH_CRITERIA) SearchCriteria searchCriteria) {
        searchCriteria.setPaginationNumber(pageNumber);
        return productService.findByNameOrDescription(searchCriteria);
    }

    @ModelAttribute(SEARCH_CRITERIA)
    public SearchCriteria initSearchCriteria() {
        return new SearchCriteria();
    }
}

