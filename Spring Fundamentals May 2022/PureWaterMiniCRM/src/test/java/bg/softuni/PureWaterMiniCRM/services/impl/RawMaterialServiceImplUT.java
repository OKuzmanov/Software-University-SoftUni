package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.Product;
import bg.softuni.PureWaterMiniCRM.models.entities.RawMaterial;
import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.RawMaterialServiceModel;
import bg.softuni.PureWaterMiniCRM.repositories.RawMaterialRepository;
import bg.softuni.PureWaterMiniCRM.services.RawMaterialService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RawMaterialServiceImplUT {
    @Mock
    private RawMaterialRepository mockRawMaterialRepo;
    @Mock
    private ModelMapper mockModelMapper;

    private RawMaterialService rawMatServiceToTest;

    @BeforeEach
    public void setup() {
        this.rawMatServiceToTest = new RawMaterialServiceImpl(mockRawMaterialRepo, mockModelMapper);
    }

    @Test
    public void testRawMaterialService_addRawMaterials() {
        RawMaterialServiceModel rmsm = new RawMaterialServiceModel(111, RawMaterialType.GLUE,
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        RawMaterial rawMaterialEntity = new RawMaterial(110, RawMaterialType.GLUE, null);
        when(mockRawMaterialRepo.findByType(rmsm.getType()))
                .thenReturn(Optional.of(rawMaterialEntity));

        when(mockModelMapper.map(rmsm.getSupplier(), Supplier.class))
                .thenReturn(null);

        rmsm.setQuantity(221);
        when(mockModelMapper.map(any(RawMaterial.class), eq(RawMaterialServiceModel.class)))
                .thenReturn(rmsm);

        RawMaterialServiceModel resultRMSM = this.rawMatServiceToTest.addRawMaterials(rmsm);

        Assertions.assertEquals(221, resultRMSM.getQuantity());
        Assertions.assertEquals(rmsm.getType(), resultRMSM.getType());
        Assertions.assertEquals(rmsm.getDeliveredAt(), resultRMSM.getDeliveredAt());
        Assertions.assertNull(resultRMSM.getUser());
        Assertions.assertNull(resultRMSM.getSupplier());
    }

    @Test
    public void testRawMaterialService_isRepoEmptyAssertTrue() {
        when(mockRawMaterialRepo.count())
                .thenReturn(Long.valueOf(0));

        boolean resultBool = this.rawMatServiceToTest.isRepoEmpty();

        Assertions.assertTrue(resultBool);
    }

    @Test
    public void testRawMaterialService_saveAll() {
        RawMaterial rm1 = new RawMaterial(11, RawMaterialType.BOTTLENECK, null);
        RawMaterial rm2 = new RawMaterial(21, RawMaterialType.GLUE, null);

        when(this.mockRawMaterialRepo.saveAll(List.of(rm1, rm2)))
                .thenReturn(List.of(rm1, rm2));

        Assertions.assertDoesNotThrow(() -> this.rawMatServiceToTest.saveAll(List.of(rm1, rm2)));
    }

    @Test
    public void testRawMaterialService_reduceQuantityBy() {
        RawMaterialType testType = RawMaterialType.GLUE;
        int testQuant = 111;

        RawMaterialServiceModel rmsm = new RawMaterialServiceModel(221, RawMaterialType.GLUE, LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        when(mockRawMaterialRepo.findByType(testType))
                .thenReturn(Optional.of(new RawMaterial(221, RawMaterialType.GLUE, null)));

        rmsm.setQuantity(rmsm.getQuantity() - testQuant);
        when(mockModelMapper.map(any(RawMaterial.class), eq(RawMaterialServiceModel.class)))
                .thenReturn(rmsm);

        RawMaterialServiceModel rmsmResult = this.rawMatServiceToTest.reduceQuantityBy(testType, testQuant);

        Assertions.assertEquals(110, rmsmResult.getQuantity());
        Assertions.assertEquals(rmsm.getType(), rmsmResult.getType());
        Assertions.assertEquals(rmsm.getDeliveredAt(), rmsmResult.getDeliveredAt());
        Assertions.assertNull(rmsmResult.getUser());
        Assertions.assertNull(rmsmResult.getSupplier() );
    }

    @Test
    public void testRawMaterialService_getQuantityByType() {

        RawMaterialType testType = RawMaterialType.GLUE;

        RawMaterial testEntity = new RawMaterial(111, RawMaterialType.GLUE, null);

        when(mockRawMaterialRepo.findByType(any(RawMaterialType.class)))
                .thenReturn(Optional.of(testEntity));

        int resultQuantity = this.rawMatServiceToTest.getQuantityByType(testType);

        Assertions.assertEquals(testEntity.getQuantity(), resultQuantity);
    }

    @Test
    public void testRawMaterialService_getQuantityByTypeEmptyOptional() {
        RawMaterialType testType = RawMaterialType.GLUE;
        int expectedQuantity = 0;

        when(mockRawMaterialRepo.findByType(any(RawMaterialType.class)))
                .thenReturn(Optional.empty());

        int resultQuantity = this.rawMatServiceToTest.getQuantityByType(testType);

        Assertions.assertEquals(expectedQuantity, resultQuantity);
    }
}
