package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.User;

public interface UserService extends BaseService<User> {
//    User findByEmailAndPassword(String email, String password);

    User findById(int id);

    User findByEmail(String name);
}
