package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.repositories.UserRepository;
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
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    private static final String GET_ALL_USERS = "select u from User u";
    private static final String GET_USER_BY_EMAIL_AND_PASSWORD = "select u from User u where u.email=:email and u.password=:password";

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        TypedQuery<User> query = entityManager.createQuery(GET_USER_BY_EMAIL_AND_PASSWORD, User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.getSingleResult();
    }

    @Override
    public User create(User entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<User> read() {
        return entityManager.createQuery(GET_ALL_USERS, User.class).getResultList();
    }

    @Override
    public User update(User entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(User entity) {
        entityManager.remove(entity);
    }
}

