package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.entities.RawMaterial;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import bg.softuni.PureWaterMiniCRM.repositories.RawMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
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
public class RawMaterialsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private RawMaterial testEntity;

    private RawMaterialRepository rawMaterialRepo;

    @Autowired
    public RawMaterialsControllerIT(RawMaterialRepository rawMaterialRepo) {
        this.rawMaterialRepo = rawMaterialRepo;
    }

    @BeforeEach
    public void setup() {
        testEntity = this.rawMaterialRepo.findByType(RawMaterialType.GLUE).get();
    }
    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_getAddWithUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/materials/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("suppliers"))
                .andExpect(view().name("addRawMaterial"));
    }

    @Test
    public void testRawMaterialsController_getAddNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/materials/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_getAllWithUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/materials/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allMaterials"))
                .andExpect(view().name("allRawMaterials"));
    }

    @Test
    public void testRawMaterialsController_getAllNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/materials/all"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_postWithValidParams() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/materials/add")
                        .param("type", RawMaterialType.BOTTLENECK.name())
                        .param("quantity", String.valueOf(10))
                        .param("deliveredAt", LocalDateTime.of(2022, Month.JULY, 29, 23, 59).toString())
                        .param("supplierName", "Euro Supply ltd.")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_postWithInvalidParams() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/materials/add")
                        .param("type", "")
                        .param("quantity", String.valueOf(-10))
                        .param("deliveredAt", LocalDateTime.of(2030, Month.JULY, 29, 23, 59).toString())
                        .param("supplierName", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attributeExists("rawMaterialAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.rawMaterialAddBindingModel"))
                .andExpect(redirectedUrl("/materials/add"));
    }

    @Test
    public void testRawMaterialsController_postNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/materials/add")
                        .param("type", RawMaterialType.BOTTLENECK.name())
                        .param("quantity", String.valueOf(10))
                        .param("deliveredAt", LocalDateTime.of(2022, Month.JULY, 29, 23, 59).toString())
                        .param("supplierName", "Euro Supply ltd.")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_getDetails() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/materials/details/" + testEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allSuppliers"))
                .andExpect(model().attributeExists("materialDetails"))
                .andExpect(view().name("material-details"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_rawMaterialsUpdate() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/materials/update/" + testEntity.getId())
                        .param("quantity", "101")
                        .param("type", testEntity.getType().name())
                        .param("deliveredAt", LocalDateTime.of(2022, Month.JULY, 29, 23, 59).toString())
                        .param("supplier", "Euro Supply ltd.")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/materials/details/" + testEntity.getId()))
                .andExpect(flash().attributeExists("isUpdateSuccess"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_rawMaterialsUpdateNoParam() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/materials/update/" + testEntity.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/materials/details/" + testEntity.getId()))
                .andExpect(flash().attributeExists("isNotUpdateSuccess"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testRawMaterialsController_deleteRawMaterial() throws Exception {
        RawMaterial testEntityToDelete = this.rawMaterialRepo.findByType(RawMaterialType.CAP_HALF_LITRE).get();
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/materials/delete/" + testEntityToDelete.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/materials/all"));
    }
}
