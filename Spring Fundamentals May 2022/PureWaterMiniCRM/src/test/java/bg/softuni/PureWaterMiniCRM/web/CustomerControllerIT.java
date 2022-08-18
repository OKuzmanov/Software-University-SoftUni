package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.repositories.CustomerRepository;
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
public class CustomerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private Customer testEntity;

    private CustomerRepository customerRepo;

    private UserRepository userRepo;

    @Autowired
    public CustomerControllerIT(CustomerRepository customerRepo, UserRepository userRepo) {
        this.customerRepo = customerRepo;
        this.userRepo = userRepo;
    }

    @BeforeEach
    public void setup() {
        int rand = new Random().nextInt();
        testEntity = new Customer("testName" + rand, "mail@gmail.com",
                "11111111", "Test Address",
                "Test Description", userRepo.findByUsername("oleg4o").get());

        customerRepo.save(testEntity);
    }

    //TODO: Create a new user in the Arrange phase
    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testCustomerController_getAddWithUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/customers/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addCustomer"));
    }

    @Test
    public void testCustomerController_getAddNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/customers/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    //TODO: Create a new user in the Arrange phase
    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testCustomerController_getAllWithUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/customers/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allCustomers"))
                .andExpect(view().name("allCustomers"));
    }

    @Test
    public void testCustomerController_getAllNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/customers/all"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testCustomerController_postWithValidParams() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/customers/add")
                        .param("companyName", "Test Company Name")
                        .param("email", "test@gmail.com")
                        .param("address", "Test Address, 78")
                        .param("phoneNumber", "12345678910")
                        .param("description", "Test Description")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testCustomerController_postWithExistingCompanyName() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/customers/add")
                        .param("companyName", "Ganchev and Brothers ltd.")
                        .param("email", "test@gmail.com")
                        .param("address", "Test Address, 78")
                        .param("phoneNumber", "12345678910")
                        .param("description", "Test Description")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(3))
                .andExpect(flash().attributeExists("customerAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.customerAddBindingModel"))
                .andExpect(flash().attributeExists("isExist"))
                .andExpect(redirectedUrl("/customers/add"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testCustomerController_postWithInValidParams() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/customers/add")
                        .param("companyName", "1")
                        .param("email", "1")
                        .param("address", "1")
                        .param("phoneNumber", "1")
                        .param("description", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attributeExists("customerAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.customerAddBindingModel"))
                .andExpect(redirectedUrl("/customers/add"));
    }

    @Test
    public void testCustomerController_postNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/customers/add")
                        .param("companyName", "Test Company Name")
                        .param("email", "test@gmail.com")
                        .param("address", "Test Address, 78")
                        .param("phoneNumber", "12345678910")
                        .param("description", "Test Description")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testCustomerController_getDetails() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/customers/details/" + testEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customerDetails"))
                .andExpect(view().name("customer-details"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testCustomerController_userUpdate() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/customers/update/" + testEntity.getId())
                        .param("companyName", "testCompanyName")
                        .param("email", "tm@gmail.com")
                        .param("phoneNumber", "11111111")
                        .param("address", "testAddress")
                        .param("description", "testDescription")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers/details/" + testEntity.getId()))
                .andExpect(flash().attributeExists("isUpdateSuccess"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testCustomerController_userUpdate_NoParams() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/customers/update/" + testEntity.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers/details/" + testEntity.getId()))
                .andExpect(flash().attributeExists("isNotUpdateSuccess"));
    }
    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testCustomerController_deleteCustomer() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/customers/delete/" + testEntity.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers/all"));

    }
}
