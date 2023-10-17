package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private static final String GET_ALL_CATEGORIES = "select c from Category c";

    @Override
    public Category findById(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Category.class, id);
    }

    @Override
    public Category create(Category entity) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(entity);
        return entity;
    }

    @Override
    public List<Category> read() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery(GET_ALL_CATEGORIES, Category.class).getResultList();
    }

    @Override
    public Category update(Category entity) {
        Session session = entityManager.unwrap(Session.class);
        return session.merge(entity);
    }

    @Override
    public void delete(Category entity) {
        Session session = entityManager.unwrap(Session.class);
        session.remove(entity);
    }
}
