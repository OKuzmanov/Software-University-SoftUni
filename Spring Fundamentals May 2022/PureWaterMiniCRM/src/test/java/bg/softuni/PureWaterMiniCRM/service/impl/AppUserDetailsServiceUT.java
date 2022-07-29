package bg.softuni.PureWaterMiniCRM.service.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.Role;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.repositories.UserRepository;
import bg.softuni.PureWaterMiniCRM.services.impl.AppUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserDetailsServiceUT {

    @Mock
    private UserRepository mockUserRepo;

    private AppUserDetailsService toTest;

    @BeforeEach
    public void setup() {
        toTest = new AppUserDetailsService(mockUserRepo);
    }

    @Test
    void testLoadUserByUsername_userExists() {
        UserEntity userEntity = new UserEntity("peshec", "Pesho", "Peshev", "topSecret", "pp@abv.bg");
        LinkedHashSet<Role> roles = new LinkedHashSet<>();
        roles.add(new Role(RoleEnum.USER));
        roles.add(new Role(RoleEnum.MODERATOR));
        userEntity.setRole(roles);

        when(mockUserRepo.findByUsername(userEntity.getUsername()))
                .thenReturn(Optional.of(userEntity));

        PureWaterUserDetails userDetails = (PureWaterUserDetails) toTest.loadUserByUsername(userEntity.getUsername());

        Assertions.assertEquals(userEntity.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(userEntity.getFirstName(), userDetails.getFirstName());
        Assertions.assertEquals(userEntity.getLastName(), userDetails.getLastName());
        Assertions.assertEquals(userEntity.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(userEntity.getFirstName() + " " + userEntity.getLastName(), userDetails.getFullName());

        Collection<GrantedAuthority> authorities = userDetails.getAuthorities();
        Assertions.assertEquals(2, authorities.size());

        Iterator<GrantedAuthority> iterator = authorities.iterator();
        Assertions.assertEquals("ROLE_" + RoleEnum.USER, iterator.next().getAuthority());
        Assertions.assertEquals("ROLE_" + RoleEnum.MODERATOR, iterator.next().getAuthority());
    }

    @Test
    void testLoadUserByUsername_userDoesNotExist() {
        UserEntity userEntity = new UserEntity("peshec", "Pesho", "Peshev", "topSecret", "pp@abv.bg");
        LinkedHashSet<Role> roles = new LinkedHashSet<>();
        roles.add(new Role(RoleEnum.USER));
        roles.add(new Role(RoleEnum.MODERATOR));
        userEntity.setRole(roles);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> toTest.loadUserByUsername(userEntity.getUsername()));
    }
}
