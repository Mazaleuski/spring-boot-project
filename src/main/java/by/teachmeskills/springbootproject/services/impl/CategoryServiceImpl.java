package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.utils.PageUtil;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.springbootproject.ShopConstants.CATEGORIES;
import static by.teachmeskills.springbootproject.ShopConstants.CATEGORY;
import static by.teachmeskills.springbootproject.ShopConstants.PRODUCTS;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.CATEGORY_PAGE;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.HOME_PAGE;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public ModelAndView create(Category entity) {
        Category category = categoryRepository.findById(entity.getId()).orElseThrow(() -> new EntityExistsException("Category exists!"));
        categoryRepository.save(category);
        return new ModelAndView();
    }

    @Override
    public List<Category> read() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(Category entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public void delete(Category entity) {
        categoryRepository.delete(entity);
    }

    @Override
    public ModelAndView findById(int id) {
        ModelAndView modelAndView = new ModelAndView(PagesPathEnum.CATEGORY_PAGE.getPath());
        Category category = categoryRepository.findById(id);
        if (Optional.ofNullable(category).isPresent()) {
            List<Product> products = productRepository.findByCategoryId(id);
            if (products.size() != 0) {
                modelAndView.addObject(PRODUCTS, products);
            } else {
                modelAndView.addObject("message", "В данной категории пока нет товаров...");
            }
            modelAndView.addObject(CATEGORY, category);
        }
        return modelAndView;
    }

    @Override
    public void downloadCategoriesToFile(HttpServletResponse response)
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        try (Writer writer = new OutputStreamWriter(response.getOutputStream())) {
            StatefulBeanToCsv<Category> beanToCsv = new StatefulBeanToCsvBuilder<Category>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(',')
                    .build();
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" + "categories.csv");
            beanToCsv.write(categoryRepository.findAll());
        }
    }

    @Override
    public ModelAndView uploadCategoriesFromFile(MultipartFile file) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/" + HOME_PAGE.getPath());
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<Category> csvToBean = new CsvToBeanBuilder<Category>(reader)
                    .withType(Category.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreQuotations(true)
                    .withSeparator(',')
                    .build();
            List<Category> categories = new ArrayList<>();
            csvToBean.forEach(categories::add);
            categoryRepository.saveAll(categories);
            return modelAndView;
        }
    }

    @Override
    public ModelAndView findAll(int pageNumber, int pageSize, String param) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(param).ascending());
        Page<Category> page = categoryRepository.findAll(paging);
        ModelMap modelParam = PageUtil.addAttributesFromPage(page, pageNumber, pageSize);
        modelParam.addAttribute(CATEGORIES, page.getContent());
        modelParam.addAttribute("id", page.getContent());
        return new ModelAndView(HOME_PAGE.getPath(), modelParam);
    }

    @Override
    public ModelAndView findById(int id, int pageNumber, int pageSize, String param) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(param).ascending());
        Page<Product> page = productRepository.findAllByCategoryId(id, paging);
        ModelMap modelMap = PageUtil.addAttributesFromPage(page, pageNumber, pageSize);
        modelMap.addAttribute(PRODUCTS, page.getContent());
        modelMap.addAttribute("id", id);
        modelMap.addAttribute(CATEGORY, categoryRepository.findById(id));
        return new ModelAndView(CATEGORY_PAGE.getPath(), modelMap);
    }
}
