package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_USER = "SELECT * FROM users WHERE email=? and password=?";
    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    private final static String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String ADD_USER = "INSERT INTO users (name,surname,birthday,email,password,balance,address,phoneNumber) values (?,?,?,?,?,0,?,?)";
    private final static String GET_ALL_USERS = "SELECT * FROM users";
    private final static String UPDATE_ADDRESS_AND_PHONE_NUMBER = "UPDATE users SET address = ?, phoneNumber = ? WHERE id = ?";

    @Override
    public User create(User entity) {
        jdbcTemplate.update(ADD_USER, entity.getName(), entity.getSurname(), entity.getBirthday(),
                entity.getEmail(), entity.getPassword(), entity.getAddress(), entity.getPhoneNumber());
        return entity;
    }

    @Override
    public List<User> read() {
        return jdbcTemplate.query(GET_ALL_USERS, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User update(User entity) {
        jdbcTemplate.update(UPDATE_ADDRESS_AND_PHONE_NUMBER, entity.getAddress(), entity.getPhoneNumber(), entity.getId());
        return entity;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_USER_BY_ID, id);
    }

    @Override
    public User findById(int id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID, new BeanPropertyRowMapper<>(User.class), id);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return jdbcTemplate.query(GET_USER, new BeanPropertyRowMapper<>(User.class),
                email, password).stream().findAny().orElse(null);
    }
}

