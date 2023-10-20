package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchCriteria;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
@Transactional
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private static final String GET_ALL_PRODUCTS = "select p from Product p";
    private static final String GET_PRODUCT_BY_NAME_OR_DESCRIPTION =
            "from Product where name like :search or description like :search";
    private static final String GET_PRODUCTS_BY_CATEGORY_ID = "select p from Product p where p.category.id=:categoryId";

    @Override
    public Product findById(int id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> findByCategoryId(int id) {
        TypedQuery<Product> query = entityManager.createQuery(GET_PRODUCTS_BY_CATEGORY_ID, Product.class);
        query.setParameter("categoryId", id);
        return query.getResultList();
    }

    @Override
    public List<Product> findByNameOrDescription(SearchCriteria searchCriteria) {
        TypedQuery<Product> query = entityManager.createQuery(GET_PRODUCT_BY_NAME_OR_DESCRIPTION, Product.class);
        query.setParameter("search", "%" + searchCriteria.getSearchString().toLowerCase() + "%");
        query.setFirstResult((searchCriteria.getPaginationNumber() - 1) * 2);
        query.setMaxResults(2);
        return query.getResultList();
    }

    @Override
    public Product create(Product entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Product> read() {
        return entityManager.createQuery(GET_ALL_PRODUCTS, Product.class).getResultList();
    }

    @Override
    public Product update(Product entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(Product entity) {
        entityManager.remove(entity);
    }
}
