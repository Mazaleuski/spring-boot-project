package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ModelAndView openProductPage(@PathVariable int id) {
        return productService.findById(id);
    }
}
