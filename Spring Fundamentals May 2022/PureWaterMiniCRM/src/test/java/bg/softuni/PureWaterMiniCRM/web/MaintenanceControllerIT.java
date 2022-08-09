package bg.softuni.PureWaterMiniCRM.web;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MaintenanceControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMaintenanceController_getMaintenancePage() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/maintenance"))
                .andExpect(status().isOk())
                .andExpect(view().name("maintenance"))
                .andExpect(handler().methodName("getMaintenanceView"));
    }
}
