package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchCriteria;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

public interface ProductService extends BaseService<Product> {
    Product findById(int id);

    List<Product> findByCategoryId(int id);

    ModelAndView findByNameOrDescription(SearchCriteria searchCriteria);

    void downloadProductsToFile(HttpServletResponse response, int categoryId)
            throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;

    ModelAndView uploadProductsFromFile(MultipartFile file) throws IOException;
}
