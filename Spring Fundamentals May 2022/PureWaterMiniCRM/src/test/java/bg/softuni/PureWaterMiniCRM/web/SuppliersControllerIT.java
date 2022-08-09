package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.Month;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SuppliersControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testSuppliersController_getAddWithUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/suppliers/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addSupplier"));
    }

    @Test
    public void testRawMaterialsController_getAddNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/suppliers/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testSuppliersController_getAllWithUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/suppliers/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allSuppliers"))
                .andExpect(view().name("allSuppliers"));
    }

    @Test
    public void testRawMaterialsController_getAllNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/suppliers/all"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_postWithValidDataValidSupplier() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/suppliers/add")
                        .param("companyName", "Test Company Name")
                        .param("email", "testMail@gmail.com")
                        .param("address", "Test Address")
                        .param("phoneNumber", "12345678910")
                        .param("description", "Test Description")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_postWithInvalidDataValidSupplier() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/suppliers/add")
                        .param("companyName", "1")
                        .param("email", "1")
                        .param("address", "1")
                        .param("phoneNumber", "1")
                        .param("description", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attributeExists("supplierAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.supplierAddBindingModel"))
                .andExpect(redirectedUrl("/suppliers/add"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_postExistingCompanyName() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/suppliers/add")
                        .param("companyName", "Euro Supply ltd.")
                        .param("email", "testMail@gmail.com")
                        .param("address", "Test Address")
                        .param("phoneNumber", "12345678910")
                        .param("description", "Test Description")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(3))
                .andExpect(flash().attributeExists("supplierAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.supplierAddBindingModel"))
                .andExpect(flash().attributeExists("isExist"))
                .andExpect(redirectedUrl("/suppliers/add"));
    }
}
