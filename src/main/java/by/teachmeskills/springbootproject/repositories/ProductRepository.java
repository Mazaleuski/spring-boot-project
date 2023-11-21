package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    List<Product> findByCategoryId(int id);

    Product findByName(String name);

    Page<Product> findAllByCategoryId(int id, Pageable paging);
}
