package bg.softuni.examprepshoppinglist.repositories;

import bg.softuni.examprepshoppinglist.models.entities.Category;
import bg.softuni.examprepshoppinglist.models.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryEnum name);
}
