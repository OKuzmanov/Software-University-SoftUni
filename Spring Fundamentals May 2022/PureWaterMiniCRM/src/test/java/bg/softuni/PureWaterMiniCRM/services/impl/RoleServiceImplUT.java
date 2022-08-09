package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.Role;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.repositories.RoleRepository;
import bg.softuni.PureWaterMiniCRM.services.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplUT {

    @Mock
    private RoleRepository mockRoleRepo;

    private RoleService roleServToTest;

    @BeforeEach
    public void setup() {
        this.roleServToTest = new RoleServiceImpl(mockRoleRepo);
    }

    @Test
    public void testRoleService_isRepoEmpty() {
        when(mockRoleRepo.count())
                .thenReturn(Long.valueOf(0));

        boolean resultBool = this.roleServToTest.isRepoEmpty();

        Assertions.assertTrue(resultBool);
    }

    @Test
    public void testRoleService_saveAll() {
        when(mockRoleRepo.saveAll(List.of()))
                .thenReturn(List.of());

        Assertions.assertDoesNotThrow(() -> this.roleServToTest.saveAll(List.of()));
    }

    @Test
    public void testRoleService_findByName() {
        RoleEnum testEnum = RoleEnum.ADMIN;
        Role testEntity = new Role(testEnum);

        when(mockRoleRepo.findByName(testEnum))
                .thenReturn(Optional.of(testEntity));

        Optional<Role> roleOpt = this.roleServToTest.findByName(testEnum);

        Assertions.assertEquals(testEnum, roleOpt.get().getName());
    }
}
