package bg.softuni.examprepbattleships.repositories;

import bg.softuni.examprepbattleships.models.entities.Category;
import bg.softuni.examprepbattleships.models.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryEnum name);
}
