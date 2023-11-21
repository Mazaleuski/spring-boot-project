package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.csv.ProductCsv;
import by.teachmeskills.springbootproject.csv.converters.ProductConverter;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchParams;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.repositories.ProductSearchSpecification;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.ProductService;
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

import static by.teachmeskills.springbootproject.ShopConstants.CATEGORIES;
import static by.teachmeskills.springbootproject.ShopConstants.PRODUCTS;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.HOME_PAGE;
import static by.teachmeskills.springbootproject.enums.PagesPathEnum.SEARCH_PAGE;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductConverter productConverter;

    @Override
    public ModelAndView create(Product entity) {
        Product product = productRepository.findById(entity.getId()).orElseThrow(() -> new EntityExistsException("Product exists!"));
        productRepository.save(product);
        return new ModelAndView();
    }

    @Override
    public List<Product> read() {
        return productRepository.findAll();
    }

    @Override
    public Product update(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public void delete(Product entity) {
        productRepository.delete(entity);
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findByCategoryId(int id) {
        return productRepository.findByCategoryId(id);
    }

    @Override
    public ModelAndView findBySearchParams(SearchParams searchParams, int pageNumber, int pageSize, String param) {
        ModelMap modelParam = new ModelMap();
        if (searchParams.getSearchKey() != null) {
            if (searchParams.getSearchKey().length() < 3) {
                modelParam.addAttribute("info", "Не менее трех символов для поиска!");
            } else {
                Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(param).ascending());
                ProductSearchSpecification productSearchSpecification = new ProductSearchSpecification(searchParams);
                Page<Product> page = productRepository.findAll(productSearchSpecification, paging);
                modelParam = PageUtil.addAttributesFromPage(page, pageNumber, pageSize);
                List<Product> productList = page.getContent();
                if (productList.size() != 0) {
                    modelParam.addAttribute(PRODUCTS, productList);
                } else {
                    modelParam.addAttribute("message", "Ничего не найдено...");
                }
            }
        }
        modelParam.addAttribute(CATEGORIES, categoryService.read());
        return new ModelAndView(SEARCH_PAGE.getPath(), modelParam);
    }

    @Override
    public void downloadProductsToFile(HttpServletResponse response, int categoryId)
            throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (Writer writer = new OutputStreamWriter(response.getOutputStream())) {
            StatefulBeanToCsv<ProductCsv> beanToCsv = new StatefulBeanToCsvBuilder<ProductCsv>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(',')
                    .build();
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + String.format("Category %d - products.csv", categoryId));
            List<ProductCsv> productsCsv = productRepository.findByCategoryId(categoryId).stream().map(productConverter::toCsv).toList();
            beanToCsv.write(productsCsv);
        }
    }

    @Override
    public ModelAndView uploadProductsFromFile(MultipartFile file) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/" + HOME_PAGE.getPath());
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<ProductCsv> csvToBean = new CsvToBeanBuilder<ProductCsv>(reader)
                    .withType(ProductCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreQuotations(true)
                    .withSeparator(',')
                    .build();
            List<ProductCsv> productsCsv = new ArrayList<>();
            csvToBean.forEach(productsCsv::add);
            productsCsv.stream().map(productConverter::fromCsv).forEach(productRepository::save);
            return modelAndView;
        }
    }
}
