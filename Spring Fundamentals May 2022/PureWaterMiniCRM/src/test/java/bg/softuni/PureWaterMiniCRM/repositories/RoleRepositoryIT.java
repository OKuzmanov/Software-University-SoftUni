package bg.softuni.PureWaterMiniCRM.repositories;

import bg.softuni.PureWaterMiniCRM.models.entities.Role;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleRepositoryIT {

    @Autowired
    private RoleRepository mockRoleRepo;

    @Test
    public void testRoleRepository_findByName() {
        Role savedEntity = this.mockRoleRepo.findByName(RoleEnum.ADMIN).get();

        Assertions.assertEquals(RoleEnum.ADMIN, savedEntity.getName());
    }
}
