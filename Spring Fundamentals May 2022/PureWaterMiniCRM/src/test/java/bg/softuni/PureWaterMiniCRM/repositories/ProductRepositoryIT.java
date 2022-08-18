package bg.softuni.PureWaterMiniCRM.repositories;

import bg.softuni.PureWaterMiniCRM.models.entities.Product;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductRepositoryIT {

    @Autowired
    private ProductRepository mockProductRepo;

    @Test
    public void testProductRepository_findByType() {
        Product resultEntity = mockProductRepo.findByType(ProductCategoryEnum.NINETEEN_LITRES).get();

        Assertions.assertEquals(ProductCategoryEnum.NINETEEN_LITRES, resultEntity.getType());
        Assertions.assertTrue(resultEntity.getQuantity() > 0);
    }
}
