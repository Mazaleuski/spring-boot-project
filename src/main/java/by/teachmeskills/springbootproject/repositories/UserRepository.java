package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String password);

}
