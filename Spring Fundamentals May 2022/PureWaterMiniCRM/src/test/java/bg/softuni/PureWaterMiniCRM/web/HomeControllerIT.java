package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testHomeController_getIndex() throws Exception {
       mockMvc
               .perform(MockMvcRequestBuilders.get("/"))
               .andExpect(status().isOk())
               .andExpect((view().name("index")));
    }

    //TODO: Create a new user in the Arrange phase
    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testHomeController_getHomeWithMockUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allUsers"))
                .andExpect(model().attributeExists("totalRevenue"))
                .andExpect(model().attributeExists("allOrders"))
                .andExpect(view().name("home"));
    }

    @Test
    public void testHomeController_getHomeNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    public void testHomeController_getAboutNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }

    //TODO: Create a new user in the Arrange phase
    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testHomeController_getAboutWithUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/about"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }
}
