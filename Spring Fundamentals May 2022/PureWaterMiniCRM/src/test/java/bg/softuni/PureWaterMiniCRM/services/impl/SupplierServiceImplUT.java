package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.exceptions.ObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.SupplierServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.repositories.SupplierRepository;
import bg.softuni.PureWaterMiniCRM.services.SupplierService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceImplUT {
    @Mock
    private SupplierRepository mockSupplierRepo;
    @Mock
    private UserService mockUserService;
    @Mock
    private ModelMapper mockModelMapper;

    private SupplierService suppServToTest;

    @BeforeEach
    public void setup() {
        this.suppServToTest = new SupplierServiceImpl(mockSupplierRepo, mockUserService, mockModelMapper);
    }

    @Test
    public void testSupplierService_addSupplier() {
        SupplierServiceModel ssm = new SupplierServiceModel(null, "testName", "tm@gmail.com", "12345678910",
                "testAddress", "testDescription");

        when(mockModelMapper.map(any(SupplierServiceModel.class), eq(Supplier.class)))
                .thenReturn(new Supplier());

        when(mockUserService.findUserByUsername(anyString()))
                .thenReturn(new UserServiceModel());

        when(mockModelMapper.map(any(UserServiceModel.class), eq(UserEntity.class)))
                .thenReturn(null);

        when(mockModelMapper.map(any(Supplier.class), eq(SupplierServiceModel.class)))
                .thenReturn(ssm);

        SupplierServiceModel resultSSM = this.suppServToTest.addSupplier(ssm, new PureWaterUserDetails(Long.valueOf(111), "test", "12345", "Oleg", "Kuzmanov", null));

        Assertions.assertEquals(ssm.getCompanyName(), resultSSM.getCompanyName());
        Assertions.assertEquals(ssm.getEmail(), resultSSM.getEmail());
        Assertions.assertEquals(ssm.getAddress(), resultSSM.getAddress());
        Assertions.assertEquals(ssm.getDescription(), resultSSM.getDescription());
        Assertions.assertEquals(ssm.getPhoneNumber(), resultSSM.getPhoneNumber());
    }

    @Test
    public void testSupplierService_findByCompanyNameValidTestName() {
        String testName = "testName";
        Supplier testEntity = new Supplier("testName", "tm@gmail.com", "12345678910", "testAddress", "testDesc", null);
        SupplierServiceModel ssm = new SupplierServiceModel(null, "testName", "tm@gmail.com", "12345678910", "testAddress", "testDesc");

        when(mockSupplierRepo.findByCompanyName(testName))
                .thenReturn(Optional.of(testEntity));

        when(mockModelMapper.map(any(Supplier.class), eq(SupplierServiceModel.class)))
                .thenReturn(ssm);

        SupplierServiceModel resultSSM = this.suppServToTest.findByCompanyName(testName);

        Assertions.assertNotNull(resultSSM);
        Assertions.assertEquals(ssm.getCompanyName(), resultSSM.getCompanyName());
        Assertions.assertEquals(ssm.getAddress(), resultSSM.getAddress());
        Assertions.assertEquals(ssm.getEmail(), resultSSM.getEmail());
        Assertions.assertEquals(ssm.getDescription(), resultSSM.getDescription());
        Assertions.assertEquals(ssm.getPhoneNumber(), resultSSM.getPhoneNumber());
    }

    @Test
    public void testSupplierService_findByCompanyNameInvalidTestName() {
        String testName = "testName";

        when(mockSupplierRepo.findByCompanyName(testName))
                .thenReturn(Optional.empty());

        SupplierServiceModel resultSSM = this.suppServToTest.findByCompanyName(testName);

        Assertions.assertNull(resultSSM);
    }

    @Test
    public void testSupplierService_findAllSuppliers() {
        Supplier s1 = new Supplier("testName", "tm@gmail.com", "12345678910", "testAddress", "testDesc", null);
        Supplier s2 = new Supplier("testName2", "tm2@gmail.com", "12345678910", "testAddress2", "testDesc2", null);

        when(mockSupplierRepo.findAll())
                .thenReturn(List.of(s1, s2));

        SupplierServiceModel ssm1 = new SupplierServiceModel(null, "testName", "tm@gmail.com", "12345678910", "testAddress", "testDesc");
        when(mockModelMapper.map(s1, SupplierServiceModel.class))
                .thenReturn(ssm1);

        SupplierServiceModel ssm2 = new SupplierServiceModel(null, "testName2", "tm2@gmail.com", "12345678910", "testAddress2", "testDesc2");
        when(mockModelMapper.map(s1, SupplierServiceModel.class))
                .thenReturn(ssm2);

        List<SupplierServiceModel> resultAllSuppliers = this.suppServToTest.findAllSuppliers();

        Assertions.assertEquals(2, resultAllSuppliers.size());
        Assertions.assertEquals(ssm1.getCompanyName(), s1.getCompanyName());
        Assertions.assertEquals(ssm1.getEmail(), s1.getEmail());
        Assertions.assertEquals(ssm1.getAddress(), s1.getAddress());
        Assertions.assertEquals(ssm1.getDescription(), s1.getDescription());
        Assertions.assertEquals(ssm1.getPhoneNumber(), s1.getPhoneNumber());
        Assertions.assertEquals(ssm2.getCompanyName(), s2.getCompanyName());
        Assertions.assertEquals(ssm2.getEmail(), s2.getEmail());
        Assertions.assertEquals(ssm2.getAddress(), s2.getAddress());
        Assertions.assertEquals(ssm2.getDescription(), s2.getDescription());
        Assertions.assertEquals(ssm2.getPhoneNumber(), s2.getPhoneNumber());
    }

    @Test
    public void testSupplierService_isRepoEmptyAssertTrue() {
        when(mockSupplierRepo.count())
                .thenReturn(Long.valueOf(0));

        boolean resultBool = this.suppServToTest.isRepoEmpty();

        Assertions.assertTrue(resultBool);
    }

    @Test
    public void testSupplierService_saveAll() {

        when(this.mockSupplierRepo.saveAll(List.of()))
                .thenReturn(List.of());

        Assertions.assertDoesNotThrow(() -> this.suppServToTest.saveAll(List.of()));
    }

    @Test
    public void testSupplierService_getRandomSupplier() {
        when(mockSupplierRepo.findById(anyLong()))
                .thenReturn(Optional.of(new Supplier()));

        Assertions.assertDoesNotThrow(() -> this.suppServToTest.getRandomSupplier());
    }

    @Test
    public void testSupplierService_findById() {
        when(mockSupplierRepo.findById(anyLong()))
                .thenReturn(Optional.of(new Supplier()));

        SupplierServiceModel ssm = new SupplierServiceModel(null, "testName", "tm@gmail.com", "12345678910", "testAddress", "testDesc");
        when(mockModelMapper.map(any(Supplier.class), eq(SupplierServiceModel.class)))
                .thenReturn(ssm);

        SupplierServiceModel resultSSM = this.suppServToTest.findById(111);

        Assertions.assertEquals(ssm.getCompanyName(), resultSSM.getCompanyName());
        Assertions.assertEquals(ssm.getEmail(), resultSSM.getEmail());
        Assertions.assertEquals(ssm.getAddress(), resultSSM.getAddress());
        Assertions.assertEquals(ssm.getDescription(), resultSSM.getDescription());
        Assertions.assertEquals(ssm.getPhoneNumber(), resultSSM.getPhoneNumber());
    }

    @Test
    public void testSupplierService_findByIdThrowObjectNotFound() {
        int testId = 111;

        when(mockSupplierRepo.findById(anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class,() -> this.suppServToTest.findById(testId));
    }
}
