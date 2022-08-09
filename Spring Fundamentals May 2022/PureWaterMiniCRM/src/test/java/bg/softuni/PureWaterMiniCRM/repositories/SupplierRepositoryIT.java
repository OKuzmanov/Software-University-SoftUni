package bg.softuni.PureWaterMiniCRM.repositories;

import bg.softuni.PureWaterMiniCRM.models.entities.Role;
import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SupplierRepositoryIT {

    @Autowired
    private SupplierRepository supplierRepo;

    @Test
    public void testSupplierRepository_findByCompanyName() {
        Supplier saveEntity = this.supplierRepo.save(new Supplier("testName", "tm@gmail.com", "12345678910", "Test Address", "Test Description", null));

        Supplier resultSuppEntity = this.supplierRepo.findByCompanyName(saveEntity.getCompanyName()).get();

        Assertions.assertEquals(saveEntity.getCompanyName(), resultSuppEntity.getCompanyName());
        Assertions.assertEquals(saveEntity.getEmail(), resultSuppEntity.getEmail());
        Assertions.assertEquals(saveEntity.getAddress(), resultSuppEntity.getAddress());
        Assertions.assertEquals(saveEntity.getDescription(), resultSuppEntity.getDescription());
        Assertions.assertEquals(saveEntity.getPhoneNumber(), resultSuppEntity.getPhoneNumber());
    }
}
