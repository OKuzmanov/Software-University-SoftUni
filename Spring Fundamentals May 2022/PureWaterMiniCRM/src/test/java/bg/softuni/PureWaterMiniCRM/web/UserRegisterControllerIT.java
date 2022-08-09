package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegistrationController_getNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    //TODO: Create a new user in the Arrange phase
    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRegistrationController_getWithLoggedUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/users/register"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    public void testRegistrationController_postWithValidParams() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/users/register")
                        .param("firstName", "Gosho")
                        .param("lastName", "Georgiev")
                        .param("username", "gogo20")
                        .param("email", "gogo20@gmail.com")
                        .param("password", "topsecret")
                        .param("confirmPassword", "topsecret")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));
    }

    @Test
    public void testRegistrationController_postWithInValidParams() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/users/register")
                        .param("firstName", "Gosho")
                        .param("lastName", "Georgiev")
                        .param("username", "1")
                        .param("email", "1")
                        .param("password", "topsecret")
                        .param("confirmPassword", "topsecret")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attributeExists("userRegisterBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.userRegisterBindingModel"))
                .andExpect(redirectedUrl("/users/register"));
    }

    //TODO: Create a new user in the Arrange phase
    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRegistrationController_postWithLoggedUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/users/register")
                        .param("firstName", "Gosho")
                        .param("lastName", "Georgiev")
                        .param("username", "gogo20")
                        .param("email", "gogo20@gmail.com")
                        .param("password", "topsecret")
                        .param("confirmPassword", "topsecret")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }
}
