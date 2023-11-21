package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.User;

public interface UserService extends BaseService<User> {
    User findById(int id);

    User findByEmail(String name);
}
