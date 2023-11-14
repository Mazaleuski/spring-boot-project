package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Category;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

public interface CategoryService extends BaseService<Category> {
    ModelAndView findById(int id);

    void downloadCategoriesToFile(HttpServletResponse response) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException;

    ModelAndView uploadCategoriesFromFile(MultipartFile file) throws IOException;

    ModelAndView findAllWithPaging(int pageNumber, int pageSize);

    ModelAndView findByIdWithPaging(int id, int pageNumber, int pageSize);
}
