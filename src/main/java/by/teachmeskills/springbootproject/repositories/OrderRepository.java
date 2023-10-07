package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    Order findById(int id);

    List<Order> findByDate(LocalDate date);
}
