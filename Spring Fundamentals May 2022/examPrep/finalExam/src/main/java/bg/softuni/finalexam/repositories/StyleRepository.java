package bg.softuni.finalexam.repositories;

import bg.softuni.finalexam.models.entities.Style;
import bg.softuni.finalexam.models.enums.StyleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StyleRepository extends JpaRepository<Style, Long> {
    Optional<Style> findByStyleName(StyleEnum style);
}
