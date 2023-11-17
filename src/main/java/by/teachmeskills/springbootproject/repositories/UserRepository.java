package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Role;
import by.teachmeskills.springbootproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String username);

    @Query("select r from Role r where r.id=:id")
    Role findRoleById(@Param("id") int id);
}
