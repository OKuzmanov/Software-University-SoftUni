package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final UserRepository userRepository;

    private UserEntity testUser;

    private UserRepository userRepo;

    @Autowired
    public UserControllerIT(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        int newRand = new Random().nextInt();
        testUser = userRepository.save(new UserEntity("testUsername" + newRand, "Oleg",
                "Kuzmanov", "12345", newRand + "tm@gmail.com"));

    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testUserController_getPrincipalDetails() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/users/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andExpect(model().attributeExists("userServiceModel"))
                .andExpect(handler().methodName("getPrincipalDetails"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testUserController_getUserDetailsById() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/users/profile/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andExpect(model().attributeExists("userServiceModel"))
                .andExpect(handler().methodName("getUserDetailsById"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testUserController_userUpdateValidInput() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/users/update/" + testUser.getId())
                        .param("firstName", "testFirstName")
                        .param("lastName", "testLastName")
                        .param("username", "testUsername")
                        .param("email", "tm@gmail.com")
                        .param("password", "topsecret")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/profile/" + testUser.getId()))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attributeExists("isUpdateSuccess"))
                .andExpect(handler().methodName("userUpdate"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testUserController_userUpdateValidInputWithRoles() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/users/update/" + testUser.getId())
                        .param("firstName", "testFirstName")
                        .param("lastName", "testLastName")
                        .param("username", "1testUsername")
                        .param("email", "1tm@gmail.com")
                        .param("password", "topsecret")
                        .param("roles", RoleEnum.ADMIN.name())
                        .param("roles", RoleEnum.USER.name())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/profile/" + testUser.getId()))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attributeExists("isUpdateSuccess"))
                .andExpect(handler().methodName("userUpdate"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testUserController_userUpdateInvalidInput() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/users/update/" + testUser.getId())
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("username", "")
                        .param("email", "")
                        .param("password", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/profile/" + testUser.getId()))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attributeExists("isNotUpdateSuccess"))
                .andExpect(handler().methodName("userUpdate"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testUserController_deleteUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/users/delete/" + testUser.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }
}
