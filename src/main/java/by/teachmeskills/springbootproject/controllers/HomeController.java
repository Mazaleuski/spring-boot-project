package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.CategoryService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static by.teachmeskills.springbootproject.ShopConstants.FILE;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {
    private final CategoryService categoryService;

    @GetMapping
    public ModelAndView openHomePage(@RequestParam(defaultValue = "0") int pageNumber,
                                     @RequestParam(defaultValue = "6") int pageSize,
                                     @RequestParam(defaultValue = "name") String param) {
        return categoryService.findAll(pageNumber, pageSize, param);
    }

    @GetMapping("/download")
    public void downloadCategoriesToFile(HttpServletResponse response) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        categoryService.downloadCategoriesToFile(response);
    }

    @PostMapping("/upload")
    public ModelAndView uploadCategoriesFromFile(@RequestParam(FILE) MultipartFile file) throws IOException {
        return categoryService.uploadCategoriesFromFile(file);
    }
}
