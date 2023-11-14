package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findById(int id);
}
