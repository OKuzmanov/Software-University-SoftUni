package bg.softuni.PureWaterMiniCRM.repositories;

import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

@SpringBootTest
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepo;

    @Test
    public void testUserRepository_findByUsername() {
        UserEntity savedEntity = this.userRepo.save(new UserEntity("testUsername" + new Random().nextInt(),
                "Oleg", "Kuzmanov", "12345", "te@gmail.com" + new Random().nextInt()));

        UserEntity resultEntity = this.userRepo.findByUsername(savedEntity.getUsername()).get();

        Assertions.assertEquals(savedEntity.getUsername(), resultEntity.getUsername());
        Assertions.assertEquals(savedEntity.getEmail(), resultEntity.getEmail());
        Assertions.assertEquals(savedEntity.getPassword(), resultEntity.getPassword());
        Assertions.assertEquals(savedEntity.getFirstName(), resultEntity.getFirstName());
        Assertions.assertEquals(savedEntity.getLastName(), resultEntity.getLastName());
    }

    @Test
    public void testUserRepository_findByEmail() {
        UserEntity savedEntity = this.userRepo.save(new UserEntity("testUsername" + new Random().nextInt(),
                "Oleg", "Kuzmanov", "12345", "te@gmail.com" + new Random().nextInt()));

        UserEntity resultEntity = this.userRepo.findByEmail(savedEntity.getEmail()).get();

        Assertions.assertEquals(savedEntity.getUsername(), resultEntity.getUsername());
        Assertions.assertEquals(savedEntity.getEmail(), resultEntity.getEmail());
        Assertions.assertEquals(savedEntity.getPassword(), resultEntity.getPassword());
        Assertions.assertEquals(savedEntity.getFirstName(), resultEntity.getFirstName());
        Assertions.assertEquals(savedEntity.getLastName(), resultEntity.getLastName());
    }

    @Test
    public void testUserRepository_findAllSortedByOrders() {
        UserEntity savedEntity1 = this.userRepo.save(new UserEntity("testUsername" + new Random().nextInt(),
                "Oleg", "Kuzmanov", "12345", "te@gmail.com" + new Random().nextInt()));
        UserEntity savedEntity2 = this.userRepo.save(new UserEntity("testUsername" + new Random().nextInt(),
                "Oleg", "Kuzmanov", "12345", "te@gmail.com" + new Random().nextInt()));
        this.userRepo.saveAll(List.of(savedEntity2, savedEntity1));

        List<UserEntity> entitiesSorted = this.userRepo.findAllSortedByOrders();

        Assertions.assertTrue(() -> entitiesSorted.size() >= 2);
    }
}
