package bg.softuni.PureWaterMiniCRM.repositories;

import bg.softuni.PureWaterMiniCRM.models.entities.RawMaterial;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RawMaterialsRepositoryIT {

    @Autowired
    private RawMaterialRepository mockRawMaterialRepository;

    @Test
    public void testRawMaterialsRepo_findByType() {
        RawMaterial savedEntity = mockRawMaterialRepository.findByType(RawMaterialType.GLUE).get();

        Assertions.assertEquals(RawMaterialType.GLUE, savedEntity.getType());
        Assertions.assertTrue(savedEntity.getQuantity() > 0);
    }
}
