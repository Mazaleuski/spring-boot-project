package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.ProductService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static by.teachmeskills.springbootproject.ShopConstants.FILE;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("/{id}")
    public ModelAndView openCategoryPage(@PathVariable int id) {
        return categoryService.findById(id);
    }

    @GetMapping("/{id}/paging")
    public ModelAndView pagedCategories(@PathVariable int id,
                                        @RequestParam(defaultValue = "0") int pageNumber,
                                        @RequestParam(defaultValue = "1") int pageSize,
                                        @RequestParam(defaultValue = "name") String param) {
        return categoryService.findById(id, pageNumber, pageSize, param);
    }

    @GetMapping("{id}/download")
    public void downloadProductsToFile(@PathVariable int id, HttpServletResponse response)
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        productService.downloadProductsToFile(response, id);
    }

    @PostMapping("/upload")
    public ModelAndView uploadProductsFromFile(@RequestParam(FILE) MultipartFile file) throws IOException {
        return productService.uploadProductsFromFile(file);
    }
}
