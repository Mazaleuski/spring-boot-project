package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
@AllArgsConstructor

public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private static final String GET_ALL_ORDERS = "select o from Order o";
    private static final String GET_ORDERS_BY_USER = "select o from Order o where o.user=:user";
    private static final String GET_ORDERS_BY_DATE = "select o from Order o where o.date=:date";

    @Override
    public Order create(Order entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Order> read() {
        return entityManager.createQuery(GET_ALL_ORDERS, Order.class).getResultList();
    }

    @Override
    public Order update(Order entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(Order entity) {
        entityManager.remove(entity);
    }

    @Override
    public List<Order> findByUser(User user) {
        TypedQuery<Order> query = entityManager.createQuery(GET_ORDERS_BY_USER, Order.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public List<Order> findByDate(LocalDate date) {
        TypedQuery<Order> query = entityManager.createQuery(GET_ORDERS_BY_DATE, Order.class);
        query.setParameter("date", date);
        return query.getResultList();
    }
}
