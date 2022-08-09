package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.exceptions.ObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.entities.Role;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.repositories.UserRepository;
import bg.softuni.PureWaterMiniCRM.services.RoleService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplUT {
    @Mock
    private UserRepository mockUserRepo;
    @Mock
    private RoleService mockRoleService;
    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private PasswordEncoder mockPasswordEncoder;

    private UserService userServiceToTest;

    @BeforeEach
    public void setup() {
        this.userServiceToTest = new UserServiceImpl(mockUserRepo, mockRoleService, mockModelMapper, mockPasswordEncoder);
    }

    @Test
    public void testUserService_findUserByUsername() {
        UserEntity testEntity = new UserEntity("olegati", "Oleg", "Kuzmanov", "12345", "tm@gmail.com");

        when(mockUserRepo.findByUsername(anyString()))
                .thenReturn(Optional.of(testEntity));

        when(mockModelMapper.map(any(UserEntity.class), eq(UserServiceModel.class)))
                .thenReturn(new UserServiceModel("olegati", "Oleg", "Kuzmanov", "12345", "tm@gmail.com", null));

        UserServiceModel resultUsm = this.userServiceToTest.findUserByUsername("");

        Assertions.assertNotNull(resultUsm);
        Assertions.assertEquals(testEntity.getUsername(), resultUsm.getUsername());
        Assertions.assertEquals(testEntity.getEmail(), resultUsm.getEmail());
        Assertions.assertEquals(testEntity.getPassword(), resultUsm.getPassword());
        Assertions.assertEquals(testEntity.getFirstName(), resultUsm.getFirstName());
        Assertions.assertEquals(testEntity.getLastName(), resultUsm.getLastName());
        Assertions.assertNull(resultUsm.getRole());
    }

    @Test
    public void testUserService_findUserByUsernameAssertNull() {

        when(mockUserRepo.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        UserServiceModel resultUsm = this.userServiceToTest.findUserByUsername("");

        Assertions.assertNull(resultUsm);
    }

    @Test
    public void testUserService_findUserByEmail() {
        UserEntity testEntity = new UserEntity("olegati", "Oleg", "Kuzmanov", "12345", "tm@gmail.com");

        when(mockUserRepo.findByEmail(anyString()))
                .thenReturn(Optional.of(testEntity));

        when(mockModelMapper.map(any(UserEntity.class), eq(UserServiceModel.class)))
                .thenReturn(new UserServiceModel("olegati", "Oleg", "Kuzmanov", "12345", "tm@gmail.com", null));

        UserServiceModel resultUsm = this.userServiceToTest.findUserByEmail(new UserServiceModel("olegati", "Oleg", "Kuzmanov", "12345", "tm@gmail.com", null));

        Assertions.assertNotNull(resultUsm);
        Assertions.assertEquals(testEntity.getUsername(), resultUsm.getUsername());
        Assertions.assertEquals(testEntity.getEmail(), resultUsm.getEmail());
        Assertions.assertEquals(testEntity.getPassword(), resultUsm.getPassword());
        Assertions.assertEquals(testEntity.getFirstName(), resultUsm.getFirstName());
        Assertions.assertEquals(testEntity.getLastName(), resultUsm.getLastName());
        Assertions.assertNull(resultUsm.getRole());
    }

    @Test
    public void testUserService_findUserByEmailAssertNull() {

        when(mockUserRepo.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        UserServiceModel resultUsm = this.userServiceToTest.findUserByEmail(new UserServiceModel("olegati", "Oleg", "Kuzmanov", "12345", "tm@gmail.com", null));

        Assertions.assertNull(resultUsm);
    }

    @Test
    public void testUserService_register() {
        UserEntity testEntity = new UserEntity("olegati", "Oleg", "Kuzmanov", "12345", "tm@gmail.com");

        when(mockModelMapper.map(any(UserServiceModel.class), eq(UserEntity.class)))
                .thenReturn(testEntity);

        when(mockRoleService.findByName(any(RoleEnum.class)))
                .thenReturn(Optional.of(new Role(RoleEnum.USER)));

        when(mockUserRepo.save(any(UserEntity.class)))
                .thenReturn(testEntity);

        UserServiceModel usm = new UserServiceModel("olegati", "Oleg", "Kuzmanov", "12345", "tm@gmail.com", null);
        when(mockModelMapper.map(any(UserEntity.class), eq(UserServiceModel.class)))
                .thenReturn(usm);

        UserServiceModel resultUSM = this.userServiceToTest.register(usm);

        Assertions.assertEquals(usm.getUsername(), resultUSM.getUsername());
        Assertions.assertEquals(usm.getEmail(), resultUSM.getEmail());
        Assertions.assertEquals(usm.getPassword(), resultUSM.getPassword());
        Assertions.assertEquals(usm.getFirstName(), resultUSM.getFirstName());
        Assertions.assertEquals(usm.getLastName(), resultUSM.getLastName());
        Assertions.assertNull(resultUSM.getRole());
    }

    @Test
    public void testUserService_findById() {
        UserEntity testEntity = new UserEntity("olegati", "Oleg", "Kuzmanov", "12345", "tm@gmail.com");

        when(mockUserRepo.findById(anyLong()))
                .thenReturn(Optional.of(testEntity));

        UserServiceModel usm = new UserServiceModel("olegati", "Oleg", "Kuzmanov", "12345", "tm@gmail.com", null);
        when(mockModelMapper.map(any(UserEntity.class), eq(UserServiceModel.class)))
                .thenReturn(usm);

        UserServiceModel resultUSM = this.userServiceToTest.findById(111);

        Assertions.assertEquals(usm.getUsername(), resultUSM.getUsername());
        Assertions.assertEquals(usm.getEmail(), resultUSM.getEmail());
        Assertions.assertEquals(usm.getPassword(), resultUSM.getPassword());
        Assertions.assertEquals(usm.getFirstName(), resultUSM.getFirstName());
        Assertions.assertEquals(usm.getLastName(), resultUSM.getLastName());
        Assertions.assertNull(resultUSM.getRole());
    }

    @Test
    public void testUserService_findByIdAssertNull() {
        when(mockUserRepo.findById(anyLong()))
                .thenReturn(Optional.empty());

        UserServiceModel resultUSM = this.userServiceToTest.findById(111);

        Assertions.assertNull(resultUSM);
    }

    @Test
    public void testUserService_isRepoEmptyAssertTrue() {
        when(mockUserRepo.count())
                .thenReturn(Long.valueOf(0));

        boolean resultBool = this.userServiceToTest.isRepoEmpty();

        Assertions.assertTrue(resultBool);
    }

    @Test
    public void testUserService_saveAll() {

        when(this.mockUserRepo.saveAll(List.of()))
                .thenReturn(List.of());

        Assertions.assertDoesNotThrow(() -> this.userServiceToTest.saveAll(List.of()));
    }

    @Test
    public void testUserService_updateUserDetails() {
        UserServiceModel testUSM = new UserServiceModel("testUsernameUpdated", "testFirstnameUpdated",
                "testLastNameUpdated", "topsecretUpdated", "tmupdated@gmail.com", null);

        UserEntity testUserEntity = new UserEntity("testUsername", "testFirstname",
                "testLastName", "topsecret", "tm@gmail.com");

        when(mockUserRepo.findById(testUserEntity.getId()))
                .thenReturn(Optional.of(testUserEntity));

        when(mockPasswordEncoder.encode(anyString()))
                .thenReturn("Encoded Pass");

        when(mockUserRepo.save(any(UserEntity.class)))
                .thenReturn(testUserEntity);

        when(mockModelMapper.map(any(UserEntity.class), eq(UserServiceModel.class)))
                .thenReturn(testUSM);

        UserServiceModel resultUSM = this.userServiceToTest.updateUserDetails(testUserEntity.getId(), testUSM);

        Assertions.assertEquals(testUSM.getUsername(), resultUSM.getUsername());
        Assertions.assertEquals(testUSM.getFirstName(), resultUSM.getFirstName());
        Assertions.assertEquals(testUSM.getLastName(), resultUSM.getLastName());
        Assertions.assertEquals(testUSM.getPassword(), resultUSM.getPassword());
        Assertions.assertEquals(testUSM.getEmail(), resultUSM.getEmail());
        Assertions.assertNull(resultUSM.getId());
        Assertions.assertNull(resultUSM.getRole());
    }

    @Test
    public void testUserService_deleteUser() {
        UserServiceModel testUSM = new UserServiceModel("testUsernameUpdated", "testFirstnameUpdated",
                "testLastNameUpdated", "topsecretUpdated", "tmupdated@gmail.com", null);

        UserEntity testUserEntity = new UserEntity("testUsername", "testFirstname",
                "testLastName", "topsecret", "tm@gmail.com");

        when(mockUserRepo.findById(testUserEntity.getId()))
                .thenReturn(Optional.of(testUserEntity));

        boolean isDeleted = this.userServiceToTest.deleteUser(testUserEntity.getId());

        Assertions.assertTrue(isDeleted);
    }

    @Test
    public void testUserService_deleteUserThrowsException() {
        UserEntity testUserEntity = new UserEntity("testUsername", "testFirstname",
                "testLastName", "topsecret", "tm@gmail.com");

        when(mockUserRepo.findById(testUserEntity.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> this.userServiceToTest.deleteUser(testUserEntity.getId()));
    }

    @Test
    public void testUserService_isUserAuthorized() {
        long testId = 111;

        PureWaterUserDetails testUserDetails =
                new PureWaterUserDetails(testId, "testUsername", "topsecret",
                        "firstName", "lastName", null);

        boolean isUserAuthorized = this.userServiceToTest.isUserAuthorized(testUserDetails, testId);

        Assertions.assertTrue(isUserAuthorized);
    }

    @Test
    public void testUserService_isUserAuthorizedWithAdmin() {
        long testId = 111;

        PureWaterUserDetails testUserDetails =
                new PureWaterUserDetails(Long.valueOf(1), "testUsername", "topsecret",
                        "firstName", "lastName", List.of(new SimpleGrantedAuthority("ROLE_" + RoleEnum.ADMIN.name())));

        boolean isUserAuthorized = this.userServiceToTest.isUserAuthorized(testUserDetails, testId);

        Assertions.assertTrue(isUserAuthorized);
    }
}
