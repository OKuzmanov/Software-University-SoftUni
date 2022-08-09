package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.entities.Order;
import bg.softuni.PureWaterMiniCRM.models.entities.Product;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.repositories.CustomerRepository;
import bg.softuni.PureWaterMiniCRM.repositories.OrderRepository;
import bg.softuni.PureWaterMiniCRM.repositories.ProductRepository;
import bg.softuni.PureWaterMiniCRM.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private OrderRepository orderRepo;

    private ProductRepository productRepo;

    private CustomerRepository customerRepo;

    private UserRepository userRepository;

    @Autowired
    public OrderControllerIT(OrderRepository orderRepo, ProductRepository productRepo, CustomerRepository customerRepo, UserRepository userRepository) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.customerRepo = customerRepo;
        this.userRepository = userRepository;
    }

    //TODO: Create a new user in the Arrange phase
    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testOrderController_getAddOrdersLoggedInUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/orders/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customers"))
                .andExpect(view().name("addOrder"));
    }

    @Test
    public void testOrderController_getAddOrdersNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/orders/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testOrderController_postWithValidParams() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/orders/add")
                        .param("name", "Ten Half Litres")
                        .param("quantity", String.valueOf(10))
                        .param("prodCategory", String.valueOf(ProductCategoryEnum.HALF_LITRE))
                        .param("expiryDate", LocalDateTime.of(2023, Month.SEPTEMBER, 29, 23, 59).toString())
                        .param("description", "Test Description")
                        .param("customer", "Ganchev and Brothers ltd.")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testOrderController_postWithInValidParams() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/orders/add")
                        .param("name", "1")
                        .param("quantity", String.valueOf(-1))
                        .param("prodCategory", String.valueOf(ProductCategoryEnum.HALF_LITRE))
                        .param("expiryDate", LocalDateTime.of(2023, Month.SEPTEMBER, 29, 23, 59).toString())
                        .param("description", "1")
                        .param("customer", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attributeExists("orderAddBindingModel"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.orderAddBindingModel"))
                .andExpect(redirectedUrl("/orders/add"));
    }

    @Test
    public void testOrderController_postNoUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/orders/add")
                        .param("name", "Ten Half Litres")
                        .param("quantity", String.valueOf(10))
                        .param("prodCategory", String.valueOf(ProductCategoryEnum.HALF_LITRE))
                        .param("expiryDate", LocalDateTime.of(2023, Month.SEPTEMBER, 29, 23, 59).toString())
                        .param("description", "Test Description")
                        .param("customer", "Ganchev and Brothers ltd.")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testOrderController_removeIdUnSuccessfulDelete() throws Exception {
        Order orderEntity = new Order("Test O", 111, ProductCategoryEnum.NINETEEN_LITRES,
                "Test Desc", LocalDateTime.of(2030, Month.AUGUST, 29, 23, 59), null, null);
        orderEntity.setTotalPrice(orderEntity.getProdCategory().getPrice().multiply(BigDecimal.valueOf(orderEntity.getQuantity())));
        Order savedEntity = orderRepo.save(orderEntity);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/orders/remove/" + savedEntity.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testOrderController_removeIdSuccessfulDelete() throws Exception {
        Order orderEntity = new Order("Test O", 111, ProductCategoryEnum.NINETEEN_LITRES,
                "Test Desc", LocalDateTime.of(2030, Month.AUGUST, 29, 23, 59), null, null);
        orderEntity.setTotalPrice(orderEntity.getProdCategory().getPrice().multiply(BigDecimal.valueOf(orderEntity.getQuantity())));
        Order savedEntity = orderRepo.save(orderEntity);

        Product prodEnt = productRepo.findByType(ProductCategoryEnum.NINETEEN_LITRES).get();
        prodEnt.setQuantity(111);
        productRepo.save(prodEnt);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/orders/remove/" + savedEntity.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testOrderController_getOrderDetails() throws Exception {

        Customer savedCustomer = customerRepo.save(new Customer("Test", "test@gmail.com", "12345678910", "Mock Address", "Test Desc", null));

        Order orderEntity = new Order("Test O", 111, ProductCategoryEnum.NINETEEN_LITRES,
                "Test Desc", LocalDateTime.of(2030, Month.AUGUST, 29, 23, 59), null, savedCustomer);
        orderEntity.setTotalPrice(orderEntity.getProdCategory().getPrice().multiply(BigDecimal.valueOf(orderEntity.getQuantity())));
        Order savedEntity = orderRepo.save(orderEntity);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/orders/details/" + savedEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("order-details"))
                .andExpect(model().attributeExists("orderDetails"))
                .andExpect(model().attributeExists("customers"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testOrderController_deleteOrderValidId() throws Exception {
        Order orderEntity = new Order("Test O", 111, ProductCategoryEnum.NINETEEN_LITRES,
                "Test Desc", LocalDateTime.of(2030, Month.AUGUST, 29, 23, 59), userRepository.findByUsername("oleg4o").get(), null);
        orderEntity.setTotalPrice(orderEntity.getProdCategory().getPrice().multiply(BigDecimal.valueOf(orderEntity.getQuantity())));
        Order savedEntity = orderRepo.save(orderEntity);

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/orders/delete/" + savedEntity.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"))
                .andExpect(handler().methodName("deleteFromOrderDetails"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testOrderController_updateOrder() throws Exception {
        Order orderEntity = new Order("Test O", 111, ProductCategoryEnum.NINETEEN_LITRES,
                "Test Desc", LocalDateTime.of(2030, Month.AUGUST, 29, 23, 59), userRepository.findByUsername("oleg4o").get(), null);
        orderEntity.setTotalPrice(orderEntity.getProdCategory().getPrice().multiply(BigDecimal.valueOf(orderEntity.getQuantity())));
        Order savedEntity = orderRepo.save(orderEntity);

        mockMvc
                .perform(MockMvcRequestBuilders.patch("/orders/update/" + savedEntity.getId())
                        .param("name", "TestUpdate")
                        .param("quantity", "111")
                        .param("prodCategory", ProductCategoryEnum.LITRE_AND_HALF.name())
                        .param("expiryDate", LocalDateTime.of(2030, Month.JULY, 29, 23, 59).toString())
                        .param("description", "Test Description Update")
                        .param("customer", "Tsare and Co.")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/details/" + savedEntity.getId()))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attributeExists("isSuccessUpdate"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testOrderController_updateOrderInvalidData() throws Exception {
        Order orderEntity = new Order("Test O", 111, ProductCategoryEnum.NINETEEN_LITRES,
                "Test Desc", LocalDateTime.of(2030, Month.AUGUST, 29, 23, 59), userRepository.findByUsername("oleg4o").get(), null);
        orderEntity.setTotalPrice(orderEntity.getProdCategory().getPrice().multiply(BigDecimal.valueOf(orderEntity.getQuantity())));
        Order savedEntity = orderRepo.save(orderEntity);

        mockMvc
                .perform(MockMvcRequestBuilders.patch("/orders/update/" + savedEntity.getId())
                        .param("name", "")
                        .param("quantity", "")
                        .param("prodCategory", "")
                        .param("expiryDate", LocalDateTime.of(1919, Month.AUGUST, 10, 23, 59).toString())
                        .param("description", "")
                        .param("customer", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/details/" + savedEntity.getId()))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attributeExists("isNotSuccessUpdate"));
    }

    @Test
    @WithUserDetails(value = "oleg4o", userDetailsServiceBeanName = "userDetailsService")
    public void testOrderController_orderHistory() throws Exception {
     mockMvc
             .perform(MockMvcRequestBuilders.get("/orders/history"))
             .andExpect(status().isOk())
             .andExpect(view().name("orders-history"))
             .andExpect(model().attributeExists("allOrders"))
             .andExpect(handler().methodName("orderHistory"));
    }
}
