package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.exceptions.ObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.entities.Order;
import bg.softuni.PureWaterMiniCRM.models.entities.Product;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.ProductServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.repositories.ProductRepository;
import bg.softuni.PureWaterMiniCRM.services.ProductService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplUT {
    @Mock
    private ProductRepository mockProductRepo;
    @Mock
    private ModelMapper mockModelMapper;

    private ProductService prodServiceToTest;

    private Product testEntity;

    private PureWaterUserDetails userDetails;

    private ProductServiceModel serviceModel;

    @BeforeEach()
    public void setup() {
        this.prodServiceToTest = new ProductServiceImpl(mockProductRepo, mockModelMapper);
        this.testEntity = new Product(101, ProductCategoryEnum.NINETEEN_LITRES, LocalDateTime.now());
        serviceModel = new ProductServiceModel(101, ProductCategoryEnum.NINETEEN_LITRES, LocalDateTime.now());
        userDetails = new PureWaterUserDetails(Long.valueOf(111), "test", "12345", "Oleg", "Kuzmanov",
                List.of(new SimpleGrantedAuthority("ROLE_" + RoleEnum.ADMIN)));
    }

    @Test
    public void testProductService_addProducts() {
        ProductServiceModel psm = new ProductServiceModel(111, ProductCategoryEnum.NINETEEN_LITRES,
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59));

        Product productEntity = new Product(110, ProductCategoryEnum.NINETEEN_LITRES);

        when(this.mockProductRepo.findByType(psm.getType()))
                .thenReturn(Optional.of(productEntity));

        when(this.mockModelMapper.map(productEntity, ProductServiceModel.class))
                .thenReturn(psm);

        ProductServiceModel resultPSM = this.prodServiceToTest.addProducts(psm);

        Assertions.assertEquals(psm.getQuantity(), resultPSM.getQuantity());
        Assertions.assertEquals(psm.getType(), resultPSM.getType());
        Assertions.assertEquals(psm.getProductionDate(), resultPSM.getProductionDate());
    }

    @Test
    public void testProductService_isRepoEmptyAssertTrue() {
        when(mockProductRepo.count())
                .thenReturn(Long.valueOf(0));

        boolean resultBool = this.prodServiceToTest.isRepoEmpty();

        Assertions.assertTrue(resultBool);
    }

    @Test
    public void testProductService_isRepoEmptyAssertFalse() {
        when(mockProductRepo.count())
                .thenReturn(Long.valueOf(10));

        boolean resultBool = this.prodServiceToTest.isRepoEmpty();

        Assertions.assertFalse(resultBool);
    }

    @Test
    public void testProductService_saveAll() {
        Product p1 = new Product(110, ProductCategoryEnum.NINETEEN_LITRES);
        Product p2 = new Product(111, ProductCategoryEnum.NINETEEN_LITRES);

        when(this.mockProductRepo.saveAll(List.of(p1, p2)))
                .thenReturn(List.of(p1, p2));

        Assertions.assertDoesNotThrow(() -> this.prodServiceToTest.saveAll(List.of(p1, p2)));
    }

    @Test
    public void testProductService_findQuantityProducedOfType() {
        ProductCategoryEnum testType = ProductCategoryEnum.NINETEEN_LITRES;

        when(mockProductRepo.findByType(testType))
                .thenReturn(Optional.of(new Product(110, testType)));

        Integer resultQuantity = this.prodServiceToTest.findQuantityProducedOfType(testType);

        Assertions.assertEquals(110, resultQuantity);
    }

    @Test
    public void testProductService_reduceQuantityBy() {
        ProductCategoryEnum testType = ProductCategoryEnum.NINETEEN_LITRES;
        int testQuantity = 110;

        when(mockProductRepo.findByType(testType))
                .thenReturn(Optional.of(new Product(170, testType)));

        Assertions.assertDoesNotThrow(() -> this.prodServiceToTest.reduceQuantityBy(testType, testQuantity));
    }

    @Test
    public void testProductService_findAll() {
        when(mockProductRepo.findAll())
                .thenReturn(List.of(new Product()));

        List<ProductServiceModel> resultCollection = prodServiceToTest.findAll();

        Assertions.assertEquals(1, resultCollection.size());
    }

    @Test
    public void testProductService_findById() {
        when(mockProductRepo.findById(anyLong()))
                .thenReturn(Optional.of(testEntity));

        when(mockModelMapper.map(any(Product.class), eq(ProductServiceModel.class)))
                .thenReturn(serviceModel);

        ProductServiceModel resultPSM = prodServiceToTest.findById(anyLong());

        Assertions.assertEquals(serviceModel.getType() ,resultPSM.getType());
        Assertions.assertEquals(serviceModel.getQuantity() ,resultPSM.getQuantity());
        Assertions.assertEquals(serviceModel.getProductionDate() ,resultPSM.getProductionDate());
    }

    @Test
    public void testProductService_findByIdThrowsException() {
        when(mockProductRepo.findById(anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> prodServiceToTest.findById(anyLong()));
    }

    @Test
    public void testProductService_updateProduct() {
        when(mockProductRepo.findById(anyLong()))
                .thenReturn(Optional.of(testEntity));

        when(mockProductRepo.save(any(Product.class)))
                .thenReturn(testEntity);

        when(mockModelMapper.map(any(Product.class), eq(ProductServiceModel.class)))
                .thenReturn(serviceModel);

        ProductServiceModel resultPSM = this.prodServiceToTest.updateProduct(anyLong(), serviceModel);

        Assertions.assertEquals(serviceModel.getType() ,resultPSM.getType());
        Assertions.assertEquals(serviceModel.getQuantity() ,resultPSM.getQuantity());
        Assertions.assertEquals(serviceModel.getProductionDate() ,resultPSM.getProductionDate());
    }

    @Test
    public void testProductService_isAdmin() {
        Assertions.assertTrue(prodServiceToTest.isAdmin(userDetails));
    }

    @Test
    public void testProductService_deleteProduct() {
        when(mockProductRepo.findById(anyLong()))
                .thenReturn(Optional.of(testEntity));

        Assertions.assertDoesNotThrow(() -> prodServiceToTest.deleteProduct(anyLong()));
    }

    @Test
    public void testProductService_deleteProductThrowsException() {
        when(mockProductRepo.findById(anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> prodServiceToTest.deleteProduct(anyLong()));
    }
}
