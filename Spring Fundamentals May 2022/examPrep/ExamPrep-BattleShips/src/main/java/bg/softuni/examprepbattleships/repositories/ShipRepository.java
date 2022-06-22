package bg.softuni.examprepbattleships.repositories;

import bg.softuni.examprepbattleships.models.entities.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShipRepository extends JpaRepository<Ship, Long> {

    @Query("FROM Ship AS s JOIN s.user AS u WHERE u.id = :id")
    List<Ship> findAllByUserId(Long id);

    @Query("FROM Ship AS s JOIN s.user AS u WHERE u.id <> :id")
    List<Ship> findAllByNotUserId(Long id);

    @Query("FROM Ship AS s ORDER BY s.name ASC, s.health ASC, s.power ASC")
    List<Ship> findAllOrdered();

    Optional<Ship> findByName(String name);
}
