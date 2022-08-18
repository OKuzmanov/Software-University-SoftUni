package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.entities.Product;
import bg.softuni.PureWaterMiniCRM.models.entities.RawMaterial;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import bg.softuni.PureWaterMiniCRM.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private Product testEntity;

    private ProductRepository productRepo;

    @Autowired
    public ProductControllerIT(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @BeforeEach
    public void setup() {
        testEntity = productRepo.findByType(ProductCategoryEnum.NINETEEN_LITRES).get();
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testProductController_getAddWithUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/products/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addProduct"));
    }

    @Test
    public void testProductController_getAddNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/products/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testProductController_getAllWithUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/products/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allProducts"))
                .andExpect(view().name("allProducts"));
    }

    @Test
    public void testProductController_getAllNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/products/all"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testProductController_postWithValidParams() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/products/add")
                        .param("type", ProductCategoryEnum.HALF_LITRE.name())
                        .param("quantity", String.valueOf(1))
                        .param("productionDate", LocalDateTime.of(2022, Month.JULY, 29, 23, 59).toString())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testProductController_postWithValidParams_InsufficientResource() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/products/add")
                        .param("type", ProductCategoryEnum.HALF_LITRE.name())
                        .param("quantity", String.valueOf(1000000))
                        .param("productionDate", LocalDateTime.of(2022, Month.JULY, 29, 23, 59).toString())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attributeExists("productAddBindingModel"))
                .andExpect(flash().attributeExists("insufficientResources"))
                .andExpect(redirectedUrl("/products/add"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testProductController_postWithInvalidParams() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/products/add")
                        .param("type", "")
                        .param("quantity", String.valueOf(-10))
                        .param("productionDate", LocalDateTime.of(2022, Month.SEPTEMBER, 29, 23, 59).toString())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attributeExists("productAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.productAddBindingModel"))
                .andExpect(redirectedUrl("/products/add"));
    }

    @Test
    public void testProductController_postNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/products/add")
                        .param("type", ProductCategoryEnum.LITRE_AND_HALF.name())
                        .param("quantity", String.valueOf(10))
                        .param("productionDate", LocalDateTime.of(2022, Month.JULY, 29, 23, 59).toString())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_getDetails() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/products/details/" + testEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("productDetails"))
                .andExpect(view().name("product-details"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_productsUpdate() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/products/update/" + testEntity.getId())
                        .param("quantity", "101")
                        .param("type", testEntity.getType().name())
                        .param("productionDate", LocalDateTime.of(2022, Month.JULY, 29, 23, 59).toString())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/details/" + testEntity.getId()))
                .andExpect(flash().attributeExists("isUpdateSuccess"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_productsUpdateNoParams() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/products/update/" + testEntity.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/details/" + testEntity.getId()))
                .andExpect(flash().attributeExists("isNotUpdateSuccess"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_deleteProduct() throws Exception {
        Product testEntityToDelete = this.productRepo.findByType(ProductCategoryEnum.LITRE_AND_HALF).get();
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/products/delete/" + testEntityToDelete.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/all"));
    }
}
