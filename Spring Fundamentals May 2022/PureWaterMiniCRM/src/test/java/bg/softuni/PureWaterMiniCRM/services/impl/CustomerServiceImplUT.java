package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.exceptions.ObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.entities.Role;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.CustomerServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.repositories.CustomerRepository;
import bg.softuni.PureWaterMiniCRM.services.CustomerService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplUT {

    @Mock
    private CustomerRepository mockCustomerRepo;

    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private UserService mockUserService;

    private CustomerService serviceToTest;

    private Customer customerEntity;

    private UserEntity userEntity;

    private CustomerServiceModel customerServiceModel;

    private PureWaterUserDetails userDetails;

    @BeforeEach
    public void setup() {
        this.serviceToTest = new CustomerServiceImpl(mockCustomerRepo, mockModelMapper, mockUserService);

        customerEntity = new Customer("testName", "mail@gmail.com",
                "12345678", "testAddress", "Test Description", null);

        customerEntity.setDeleted(false);

        customerServiceModel = new CustomerServiceModel("testName", "mail@gmail.com",
                "12345678", "testAddress", "Test Description", null);

        userEntity = new UserEntity("username", "firstName", "lastName", "password", "mail@gmail.com");

        userDetails = new PureWaterUserDetails(Long.valueOf(111), "test", "12345", "Oleg", "Kuzmanov", null);
    }

    @Test
    public void testAddCustomer() {
        UserEntity mockUserEntity = new UserEntity("olegati", "Oleg", "Kuzmanov", "12345", "ok@abv.bg");

        UserServiceModel mockUSM = new UserServiceModel("olegati", "Oleg", "Kuzmanov", "12345", "ok@abv.bg", new LinkedHashSet<>());
        LinkedHashSet<Role> roles = new LinkedHashSet<>();
        roles.add(new Role(RoleEnum.USER));
        mockUSM.setRole(roles);

        Customer mockCustomerEntity = new Customer("Los Pepes", "lp@gmail.com", "0878431192", "Varna, Hristo Botev Street", "Test Description", mockUserEntity);

        CustomerServiceModel mockCSM = new CustomerServiceModel("Los Pepes", "lp@gmail.com", "0878431192", "Varna, Hristo Botev Street", "Test Description", mockUSM);

        PureWaterUserDetails mockPureWaterUserDetails = new PureWaterUserDetails(Long.valueOf(111), "olegati", "12345", "Oleg",
                "Kuzmanov", List.of(new SimpleGrantedAuthority("ROLE_" + RoleEnum.USER.name())));

        when(mockModelMapper.map(mockCSM, Customer.class))
                .thenReturn(mockCustomerEntity);

        when(mockUserService.findUserByUsername(mockPureWaterUserDetails.getUsername()))
                .thenReturn(mockUSM);

        when(mockModelMapper.map(mockUSM, UserEntity.class))
                .thenReturn(mockUserEntity);

        when(mockCustomerRepo.save(mockCustomerEntity))
                .thenReturn(mockCustomerEntity);

        when(mockModelMapper.map(mockCustomerEntity, CustomerServiceModel.class))
                .thenReturn(mockCSM);

        when(mockModelMapper.map(mockUserEntity, UserServiceModel.class))
                .thenReturn(mockUSM);

        CustomerServiceModel resultCSM = this.serviceToTest.addCustomer(mockCSM,
                mockPureWaterUserDetails);

        Assertions.assertEquals(mockCSM.getCompanyName(), resultCSM.getCompanyName());
        Assertions.assertEquals(mockCSM.getAddress(), resultCSM.getAddress());
        Assertions.assertEquals(mockCSM.getEmail(), resultCSM.getEmail());
        Assertions.assertEquals(mockCSM.getDescription(), resultCSM.getDescription());
        Assertions.assertEquals(mockCSM.getPhoneNumber(), resultCSM.getPhoneNumber());
    }

    @Test
    public void testFindCustomerByCompanyName() {
        UserEntity mockUserEntity = new UserEntity("olegati", "Oleg", "Kuzmanov", "12345", "ok@abv.bg");

        UserServiceModel mockUSM = new UserServiceModel("olegati", "Oleg", "Kuzmanov", "12345", "ok@abv.bg", new LinkedHashSet<>());
        LinkedHashSet<Role> roles = new LinkedHashSet<>();
        roles.add(new Role(RoleEnum.USER));
        mockUSM.setRole(roles);

        Customer mockCustomerEntity = new Customer("Los Pepes", "lp@gmail.com", "0878431192", "Varna, Hristo Botev Street", "Test Description", mockUserEntity);

        CustomerServiceModel mockCSM = new CustomerServiceModel("Los Pepes", "lp@gmail.com", "0878431192", "Varna, Hristo Botev Street", "Test Description", mockUSM);

        when(mockCustomerRepo.findByCompanyName(mockCSM.getCompanyName()))
                .thenReturn(Optional.of(mockCustomerEntity));

        when(mockModelMapper.map(mockCustomerEntity, CustomerServiceModel.class))
                .thenReturn(mockCSM);

        CustomerServiceModel resultCSM = this.serviceToTest.findCustomerByCompanyName(mockCSM.getCompanyName());

        Assertions.assertEquals(mockCSM.getCompanyName(), resultCSM.getCompanyName());
        Assertions.assertEquals(mockCSM.getAddress(), resultCSM.getAddress());
        Assertions.assertEquals(mockCSM.getEmail(), resultCSM.getEmail());
        Assertions.assertEquals(mockCSM.getDescription(), resultCSM.getDescription());
        Assertions.assertEquals(mockCSM.getPhoneNumber(), resultCSM.getPhoneNumber());
    }

    @Test
    public void testFindCustomerByCompanyName_returnNull() {
        UserServiceModel mockUSM = new UserServiceModel("olegati", "Oleg", "Kuzmanov", "12345", "ok@abv.bg", new LinkedHashSet<>());
        LinkedHashSet<Role> roles = new LinkedHashSet<>();
        roles.add(new Role(RoleEnum.USER));
        mockUSM.setRole(roles);

        CustomerServiceModel mockCSM = new CustomerServiceModel("Los Pepes", "lp@gmail.com", "0878431192", "Varna, Hristo Botev Street", "Test Description", mockUSM);

        when(mockCustomerRepo.findByCompanyName(mockCSM.getCompanyName()))
                .thenReturn(Optional.empty());

        CustomerServiceModel resultCSM = this.serviceToTest.findCustomerByCompanyName(mockCSM.getCompanyName());

        Assertions.assertNull(resultCSM);
    }

    @Test
    public void testFindById() {
        UserEntity mockUserEntity = new UserEntity("olegati", "Oleg", "Kuzmanov", "12345", "ok@abv.bg");

        Customer mockCustomerEntity = new Customer("Los Pepes", "lp@gmail.com", "0878431192", "Varna, Hristo Botev Street", "Test Description", mockUserEntity);
        mockCustomerEntity.setId(Long.valueOf(1));

        when(mockCustomerRepo.findById(mockCustomerEntity.getId()))
                .thenReturn(Optional.of(mockCustomerEntity));

        Customer resultCustomerEntity = this.serviceToTest.findById(mockCustomerEntity.getId().intValue());

        Assertions.assertEquals(mockCustomerEntity.getCompanyName(), resultCustomerEntity.getCompanyName());
        Assertions.assertEquals(mockCustomerEntity.getAddress(), resultCustomerEntity.getAddress());
        Assertions.assertEquals(mockCustomerEntity.getEmail(), resultCustomerEntity.getEmail());
        Assertions.assertEquals(mockCustomerEntity.getDescription(), resultCustomerEntity.getDescription());
        Assertions.assertEquals(mockCustomerEntity.getPhoneNumber(), resultCustomerEntity.getPhoneNumber());
    }

    @Test
    public void testGetAllCustomers() {
        Customer testC1 = new Customer("Test1", "testmail@gmail.com", "12345678910", "Test Address", "Test Description", null);
        Customer testC2 = new Customer("Test2", "testmail2@gmail.com", "12345678910", "Test Address 2", "Test Description 2", null);

        when(mockCustomerRepo.findAll())
                .thenReturn(List.of(testC1, testC2));

        when(mockModelMapper.map(testC1, CustomerServiceModel.class))
                .thenReturn(new CustomerServiceModel("Test1", "testmail@gmail.com", "12345678910",
                        "Test Address", "Test Description", null));

        when(mockModelMapper.map(testC2, CustomerServiceModel.class))
                .thenReturn(new CustomerServiceModel("Test2", "testmail2@gmail.com", "12345678910",
                        "Test Address 2", "Test Description 2", null));

        List<CustomerServiceModel> resultAllCustomers = this.serviceToTest.getAllCustomers();

        Assertions.assertEquals(2, resultAllCustomers.size());
        Assertions.assertEquals(testC1.getCompanyName(), resultAllCustomers.get(0).getCompanyName());
        Assertions.assertEquals(testC1.getEmail(), resultAllCustomers.get(0).getEmail());
        Assertions.assertEquals(testC1.getAddress(), resultAllCustomers.get(0).getAddress());
        Assertions.assertEquals(testC1.getPhoneNumber(), resultAllCustomers.get(0).getPhoneNumber());
        Assertions.assertEquals(testC1.getDescription(), resultAllCustomers.get(0).getDescription());
        Assertions.assertNull(resultAllCustomers.get(0).getUser());
        Assertions.assertEquals(testC2.getCompanyName(), resultAllCustomers.get(1).getCompanyName());
        Assertions.assertEquals(testC2.getEmail(), resultAllCustomers.get(1).getEmail());
        Assertions.assertEquals(testC2.getAddress(), resultAllCustomers.get(1).getAddress());
        Assertions.assertEquals(testC2.getPhoneNumber(), resultAllCustomers.get(1).getPhoneNumber());
        Assertions.assertEquals(testC2.getDescription(), resultAllCustomers.get(1).getDescription());
        Assertions.assertNull(resultAllCustomers.get(1).getUser());
    }

    @Test
    public void testCustomerService_isRepoEmptyAssertTrue() {
        when(mockCustomerRepo.count())
                .thenReturn(Long.valueOf(0));

        boolean resultBool = this.serviceToTest.isRepoEmpty();

        Assertions.assertTrue(resultBool);
    }

    @Test
    public void testCustomerService_isRepoEmptyAssertFalse() {
        when(mockCustomerRepo.count())
                .thenReturn(Long.valueOf(1));

        boolean resultBool = this.serviceToTest.isRepoEmpty();

        Assertions.assertFalse(resultBool);
    }

    @Test
    public void testCustomerService_updateCustomer() {
        long testId = 111;

        when(mockCustomerRepo.findById(anyLong()))
                .thenReturn(Optional.of(customerEntity));

        when(mockCustomerRepo.save(any(Customer.class)))
                .thenReturn(customerEntity);

        when(mockModelMapper.map(any(Customer.class), eq(CustomerServiceModel.class)))
                .thenReturn(customerServiceModel);

        CustomerServiceModel resultCSM = serviceToTest.updateCustomer(testId, customerServiceModel);

        Assertions.assertEquals(customerServiceModel.getCompanyName(), resultCSM.getCompanyName());
        Assertions.assertEquals(customerServiceModel.getEmail(), resultCSM.getEmail());
        Assertions.assertEquals(customerServiceModel.getAddress(), resultCSM.getAddress());
        Assertions.assertEquals(customerServiceModel.getDescription(), resultCSM.getDescription());
        Assertions.assertEquals(customerServiceModel.getPhoneNumber(), resultCSM.getPhoneNumber());
    }

    @Test
    public void testCustomerService_updateCustomerThrowsException() {
        long testId = 111;

        when(mockCustomerRepo.findById(anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class,() -> serviceToTest.updateCustomer(testId, customerServiceModel));
    }

    @Test
    public void testCustomerService_isOwnerOrAdmin_Owner() {
        long testId = 111;

        userEntity.setId(testId);

        customerEntity.setUser(userEntity);

        when(mockCustomerRepo.findById(anyLong()))
                .thenReturn(Optional.of(customerEntity));

        Assertions.assertTrue(this.serviceToTest.isOwnerOrAdmin(userDetails, testId));
    }

    @Test
    public void testCustomerService_isOwnerOrAdmin_Admin() {
        long testId = 111;

        userEntity.setId(Long.valueOf(1));

        customerEntity.setUser(userEntity);

        when(mockCustomerRepo.findById(anyLong()))
                .thenReturn(Optional.of(customerEntity));

        userDetails.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_" + RoleEnum.ADMIN)));

        Assertions.assertTrue(this.serviceToTest.isOwnerOrAdmin(userDetails, testId));
    }

    @Test
    public void testCustomerService_findAll() {
        when(mockCustomerRepo.findAll())
                .thenReturn(List.of(customerEntity));

        List<Customer> resultAllCustomers = serviceToTest.findAll();

        Assertions.assertEquals(1, resultAllCustomers.size());
    }

    @Test
    public void testCustomerService_deleteCustomer() {
        when(mockCustomerRepo.findById(anyLong()))
                .thenReturn(Optional.of(customerEntity));

        when(mockCustomerRepo.save(any(Customer.class)))
                .thenReturn(customerEntity);

        Assertions.assertTrue(() -> serviceToTest.deleteCustomer(anyLong()));
    }
}
