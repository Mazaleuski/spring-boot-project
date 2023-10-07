package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private static final String GET_ALL_CATEGORIES = "SELECT * FROM categories";
    private static final String GET_CATEGORY_BY_ID = "SELECT * FROM categories WHERE id=?";
    private static final String ADD_CATEGORY = "INSERT INTO categories (name,imagePath,rating) values (?,?,?)";
    private final static String DELETE_CATEGORY_BY_ID = "DELETE FROM categories WHERE id = ?";
    private final static String UPDATE_IMAGE_PATH_AND_RATING_BY_ID = "UPDATE categories SET imagePath = ?, rating = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Category create(Category entity) {
        jdbcTemplate.update(ADD_CATEGORY, entity.getName(), entity.getImagePath(), entity.getRating());
        return entity;
    }

    @Override
    public List<Category> read() {
        return jdbcTemplate.query(GET_ALL_CATEGORIES, new BeanPropertyRowMapper<>(Category.class));
    }

    @Override
    public Category update(Category entity) {
        jdbcTemplate.update(UPDATE_IMAGE_PATH_AND_RATING_BY_ID, entity.getImagePath(), entity.getRating(), entity.getId());
        return entity;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_CATEGORY_BY_ID, id);
    }

    @Override
    public Category findById(int id) {
        return jdbcTemplate.queryForObject(GET_CATEGORY_BY_ID, new BeanPropertyRowMapper<>(Category.class), id);
    }
}
