package by.teachmeskills.springbootproject.csv.converters;

import by.teachmeskills.springbootproject.csv.ProductCsv;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductConverter {

    private final CategoryRepository categoryRepository;

    public Product fromCsv(ProductCsv productCsv) {
        return Optional.ofNullable(productCsv).map(p -> Product.builder()
                        .name(productCsv.getName())
                        .price(productCsv.getPrice())
                        .category(categoryRepository.findById(productCsv.getCategoryId()))
                        .description(productCsv.getDescription())
                        .imagePath(productCsv.getImagePath())
                        .build())
                .orElse(null);
    }

    public ProductCsv toCsv(Product product) {
        return Optional.ofNullable(product).map(p -> ProductCsv.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .categoryId(product.getCategory().getId())
                        .description(product.getDescription())
                        .imagePath(product.getImagePath())
                        .build())
                .orElse(null);
    }
}
