package bg.softuni.examprepbattleships.repositories;

import bg.softuni.examprepbattleships.models.entities.User;
import bg.softuni.examprepbattleships.models.serviceModels.UserServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
