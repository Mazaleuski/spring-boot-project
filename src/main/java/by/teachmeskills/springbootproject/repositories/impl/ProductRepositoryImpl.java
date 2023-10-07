package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private static final String GET_PRODUCTS_BY_CATEGORY_ID = "SELECT * FROM products WHERE categoryId=?";
    private static final String GET_PRODUCT_BY_ID = "SELECT * FROM products WHERE id=?";
    private final static String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE id = ?";
    private final static String GET_ALL_PRODUCTS = "SELECT * FROM products";
    private final static String UPDATE_DESCRIPTION_AND_PRICE_BY_ID = "UPDATE products SET description = ?, price = ? WHERE id = ?";
    private static final String ADD_PRODUCT = "INSERT INTO products (name,description,price,categoryId,imagePath) values (?,?,?,?,?)";
    private static final String GET_PRODUCT_BY_NAME_OR_DESCRIPTION = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ? ORDER BY name ASC;";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Product create(Product entity) {
        jdbcTemplate.update(ADD_PRODUCT, entity.getName(), entity.getDescription(), entity.getPrice(), entity.getCategoryId(), entity.getImagePath());
        return entity;
    }

    @Override
    public List<Product> read() {
        return jdbcTemplate.query(GET_ALL_PRODUCTS, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public Product update(Product entity) {
        jdbcTemplate.update(UPDATE_DESCRIPTION_AND_PRICE_BY_ID, entity.getDescription(), entity.getPrice(), entity.getId());
        return entity;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_PRODUCT_BY_ID, id);
    }

    @Override
    public Product findById(int id) {
        return jdbcTemplate.queryForObject(GET_PRODUCT_BY_ID, new BeanPropertyRowMapper<>(Product.class), id);
    }

    @Override
    public List<Product> findByCategoryId(int id) {
        return jdbcTemplate.query(GET_PRODUCTS_BY_CATEGORY_ID, new BeanPropertyRowMapper<>(Product.class), id);
    }

    @Override
    public List<Product> findByNameOrDescription(String search) {
        search = "%" + search.trim() + "%";
        return jdbcTemplate.query(GET_PRODUCT_BY_NAME_OR_DESCRIPTION, new BeanPropertyRowMapper<>(Product.class), search);
    }
}
