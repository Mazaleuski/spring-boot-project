package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchCriteria;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    Product findById(int id);

    List<Product> findByCategoryId(int id);

    List<Product> findByNameOrDescription(SearchCriteria searchCriteria);

    Product findByName(String name);
}
