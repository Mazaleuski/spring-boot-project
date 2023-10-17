package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
        Session session = entityManager.unwrap(Session.class);
        session.persist(entity);
        return entity;
    }

    @Override
    public List<Order> read() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery(GET_ALL_ORDERS, Order.class).getResultList();
    }

    @Override
    public Order update(Order entity) {
        Session session = entityManager.unwrap(Session.class);
        return session.merge(entity);
    }

    @Override
    public void delete(Order entity) {
        Session session = entityManager.unwrap(Session.class);
        session.remove(entity);
    }

    @Override
    public List<Order> findByUser(User user) {
        Session session = entityManager.unwrap(Session.class);
        Query<Order> query = session.createQuery(GET_ORDERS_BY_USER, Order.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public List<Order> findByDate(LocalDate date) {
        Session session = entityManager.unwrap(Session.class);
        Query<Order> query = session.createQuery(GET_ORDERS_BY_DATE, Order.class);
        query.setParameter("date", date);
        return query.getResultList();
    }
}
