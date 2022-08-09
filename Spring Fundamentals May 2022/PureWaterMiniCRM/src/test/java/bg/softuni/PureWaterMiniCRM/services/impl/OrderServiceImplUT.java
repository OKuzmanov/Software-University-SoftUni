package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.exceptions.ApiObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.exceptions.ObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.entities.Order;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.repositories.OrderRepository;
import bg.softuni.PureWaterMiniCRM.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplUT {
    @Mock
    private OrderRepository mockOrderRepo;
    @Mock
    private UserService mockUserService;
    @Mock
    private CustomerService mockCustomerService;
    @Mock
    private ProductService mockProductService;
    @Mock
    private OrderHistoryService mockOrderHistoryService;
    @Mock
    private ModelMapper mockModelMapper;

    private OrderService orderServiceToTest;

    @BeforeEach
    public void setup() {
        this.orderServiceToTest = new OrderServiceImpl(mockOrderRepo, mockUserService, mockCustomerService, mockProductService,
                mockOrderHistoryService, mockModelMapper);
    }

    @Test
    public void testOrderService_testAddOrder() {
        OrderServiceModel osm = new OrderServiceModel("testName", ProductCategoryEnum.NINETEEN_LITRES.getPrice().multiply(BigDecimal.valueOf(111)), 111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        Order orderEntity = new Order("testName", 111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        PureWaterUserDetails mockUserDetails = new PureWaterUserDetails(Long.valueOf(111), "olegati", "12345", "Oleg",
                "Kuzmanov", List.of(new SimpleGrantedAuthority("ROLE_" + RoleEnum.USER.name())));

        when(mockModelMapper.map(osm, Order.class))
                .thenReturn(orderEntity);

        when(mockUserService.findUserByUsername(mockUserDetails.getUsername()))
                .thenReturn(null);

        when(mockModelMapper.map(null, UserEntity.class))
                .thenReturn(null);

        when(mockModelMapper.map(osm.getCustomer(), Customer.class))
                .thenReturn(null);

        when(mockOrderRepo.save(orderEntity))
                .thenReturn(orderEntity);

        when(mockModelMapper.map(orderEntity, OrderServiceModel.class))
                .thenReturn(osm);

        OrderServiceModel resultOSM = this.orderServiceToTest.addOrder(osm, mockUserDetails);

        Assertions.assertEquals(osm.getName(), resultOSM.getName());
        Assertions.assertEquals(osm.getQuantity(), resultOSM.getQuantity());
        Assertions.assertEquals(osm.getDescription(), resultOSM.getDescription());
        Assertions.assertEquals(osm.getExpiryDate(), resultOSM.getExpiryDate());
        Assertions.assertEquals(osm.getProdCategory(), resultOSM.getProdCategory());
        Assertions.assertEquals(osm.getTotalPrice(), resultOSM.getTotalPrice());
        Assertions.assertNull(resultOSM.getUser());
        Assertions.assertNull(resultOSM.getCustomer());
    }

    @Test
    public void testOrderService_testDeleteByIdThrowsException() {
        Long testId = Long.valueOf(111);
        when(mockOrderRepo.findById(testId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> this.orderServiceToTest.ifReadyDeleteById(testId));
    }

    @Test
    public void testOrderService_testDeleteByIdAssertFalse() {
        Order orderEntity = new Order("testName", 111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        Long testId = Long.valueOf(111);

        when(mockOrderRepo.findById(testId))
                .thenReturn(Optional.of(orderEntity));

        when(mockProductService.findQuantityProducedOfType(orderEntity.getProdCategory()))
                .thenReturn(13);

        Assertions.assertFalse(this.orderServiceToTest.ifReadyDeleteById(testId));
    }

    @Test
    public void testOrderService_testDeleteByIdAssertTrue() {
        Order orderEntity = new Order("testName", 111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        Long testId = Long.valueOf(111);

        when(mockOrderRepo.findById(testId))
                .thenReturn(Optional.of(orderEntity));

        when(mockProductService.findQuantityProducedOfType(orderEntity.getProdCategory()))
                .thenReturn(111);

        // ERROR: no instance(s) of type variable(s) T exist so that void conforms to T
//        when(this.mockOrderRepo.deleteById(testId));
//
//        when(mockProductService.reduceQuantityBy(orderEntity.getProdCategory(), orderEntity.getQuantity()));

        Assertions.assertTrue(this.orderServiceToTest.ifReadyDeleteById(testId));
    }

    @Test
    public void testOrderService_updateOrder() {

        OrderServiceModel osm = new OrderServiceModel("testName", ProductCategoryEnum.NINETEEN_LITRES.getPrice().multiply(BigDecimal.valueOf(111)),
                111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        long testId = 111;

        Order orderEntity = new Order();

        when(this.mockOrderRepo.findById(testId))
                .thenReturn(Optional.of(orderEntity));

        when(this.mockOrderRepo.save(orderEntity))
                .thenReturn(orderEntity);

        when(this.mockModelMapper.map(orderEntity, OrderServiceModel.class))
                .thenReturn(osm);

        OrderServiceModel resultOSM = this.orderServiceToTest.updateOrderRest(testId, osm);

        Assertions.assertEquals(osm.getName(), resultOSM.getName());
        Assertions.assertEquals(osm.getQuantity(), resultOSM.getQuantity());
        Assertions.assertEquals(osm.getDescription(), resultOSM.getDescription());
        Assertions.assertEquals(osm.getTotalPrice(), resultOSM.getTotalPrice());
        Assertions.assertEquals(osm.getProdCategory(), resultOSM.getProdCategory());
        Assertions.assertEquals(osm.getExpiryDate(), resultOSM.getExpiryDate());
        Assertions.assertNull(resultOSM.getUser());
        Assertions.assertNull(resultOSM.getCustomer());
    }

    @Test
    public void testOrderService_updateOrderThrowException() {

        OrderServiceModel osm = new OrderServiceModel("testName", ProductCategoryEnum.NINETEEN_LITRES.getPrice().multiply(BigDecimal.valueOf(111)),
                111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        long testId = 111;

        Order orderEntity = new Order();

        when(this.mockOrderRepo.findById(testId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ApiObjectNotFoundException.class,() -> this.orderServiceToTest.updateOrderRest(testId, osm));
    }

    @Test
    public void testCustomerService_isRepoEmptyAssertTrue() {
        when(mockOrderRepo.count())
                .thenReturn(Long.valueOf(0));

        boolean resultBool = this.orderServiceToTest.isRepoEmpty();

        Assertions.assertTrue(resultBool);
    }

    @Test
    public void testCustomerService_isRepoEmptyAssertFalse() {
        when(mockOrderRepo.count())
                .thenReturn(Long.valueOf(1));

        boolean resultBool = this.orderServiceToTest.isRepoEmpty();

        Assertions.assertFalse(resultBool);
    }

    @Test
    public void testOrderService_deleteTwoOverDueOrders() {
        Order order1 = new Order("testName1", 111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description1",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);
        Order order2 = new Order("testName2", 111, ProductCategoryEnum.HALF_LITRE, "Test Description2",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        when(this.mockOrderRepo.findAll())
                .thenReturn(List.of(order1, order2));

        int countOverdue = this.orderServiceToTest.deleteOverDueOrders();

        Assertions.assertEquals(2, countOverdue);
    }

    @Test
    public void testOrderService_deleteZeroOverDueOrders() {
        Order order1 = new Order("testName1", 111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description1",
                LocalDateTime.of(2030, Month.JULY, 29, 23, 59), null, null);
        Order order2 = new Order("testName2", 111, ProductCategoryEnum.HALF_LITRE, "Test Description2",
                LocalDateTime.of(2030, Month.JULY, 29, 23, 59), null, null);

        when(this.mockOrderRepo.findAll())
                .thenReturn(List.of(order1, order2));

        int countOverdue = this.orderServiceToTest.deleteOverDueOrders();

        Assertions.assertEquals(0, countOverdue);
    }

    @Test
    public void testOrderService_completeTwoOrders() {
        Order order1 = new Order("testName1", 111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description1",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);
        Order order2 = new Order("testName2", 214, ProductCategoryEnum.HALF_LITRE, "Test Description2",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        when(this.mockOrderRepo.findAll())
                .thenReturn(List.of(order1, order2));

        when(this.mockProductService.findQuantityProducedOfType(order1.getProdCategory()))
                .thenReturn(111);

        when(this.mockProductService.findQuantityProducedOfType(order2.getProdCategory()))
                .thenReturn(214);

        int countComplete = this.orderServiceToTest.completeOrders();

        Assertions.assertEquals(2, countComplete);
    }

    @Test
    public void testOrderService_completeOneOrders() {
        Order order1 = new Order("testName1", 111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description1",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);
        Order order2 = new Order("testName2", 214, ProductCategoryEnum.HALF_LITRE, "Test Description2",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        when(this.mockOrderRepo.findAll())
                .thenReturn(List.of(order1, order2));

        when(this.mockProductService.findQuantityProducedOfType(order1.getProdCategory()))
                .thenReturn(111);

        when(this.mockProductService.findQuantityProducedOfType(order2.getProdCategory()))
                .thenReturn(0);

        int countComplete = this.orderServiceToTest.completeOrders();

        Assertions.assertEquals(1, countComplete);
    }

    @Test
    public void testOrderService_completeZeroOrders() {
        Order order1 = new Order("testName1", 111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description1",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);
        Order order2 = new Order("testName2", 214, ProductCategoryEnum.HALF_LITRE, "Test Description2",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        when(this.mockOrderRepo.findAll())
                .thenReturn(List.of(order1, order2));

        when(this.mockProductService.findQuantityProducedOfType(order1.getProdCategory()))
                .thenReturn(0);

        when(this.mockProductService.findQuantityProducedOfType(order2.getProdCategory()))
                .thenReturn(0);

        int countComplete = this.orderServiceToTest.completeOrders();

        Assertions.assertEquals(0, countComplete);
    }

    @Test
    public void testOrderService_saveAll() {
        Order order1 = new Order("testName1", 111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description1",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);
        Order order2 = new Order("testName2", 214, ProductCategoryEnum.HALF_LITRE, "Test Description2",
                LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        when(this.mockOrderRepo.saveAll(List.of(order1, order2)))
                .thenReturn(List.of(order1, order2));

        this.orderServiceToTest.saveAll(List.of(order1, order2));
    }

    @Test
    public void testOrderService_isOwnerUserIsOwner() {
        PureWaterUserDetails userDetails = new PureWaterUserDetails(Long.valueOf(111), "benati", "12345", "Oleg", "Kuzmanov",
                List.of(new SimpleGrantedAuthority("ROLE_" + RoleEnum.ADMIN.name())));

        long testId = 111;

        when(mockOrderRepo.findById(anyLong()))
                .thenReturn(Optional.of(new Order("testName", 111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description",
                        LocalDateTime.of(2030, Month.JULY, 29, 23, 59),
                        new UserEntity("benati", "Albena", "Yazovska", "12345", "test.mail@gmail.com"), null)));

        boolean isOwner = this.orderServiceToTest.isOwnerOrAdmin(userDetails, testId);

        Assertions.assertTrue(isOwner);
    }

    @Test
    public void testOrderService_isOwnerAdminRole() {
        PureWaterUserDetails userDetails = new PureWaterUserDetails(Long.valueOf(111), "TestUser", "12345", "Oleg", "Kuzmanov",
                List.of(new SimpleGrantedAuthority("ROLE_" + RoleEnum.ADMIN.name())));

        long testId = 111;

        when(mockOrderRepo.findById(anyLong()))
                .thenReturn(Optional.of(new Order("testName", 111, ProductCategoryEnum.NINETEEN_LITRES, "Test Description",
                        LocalDateTime.of(2030, Month.JULY, 29, 23, 59),
                        new UserEntity("benati", "Albena", "Yazovska", "12345", "test.mail@gmail.com"), null)));

        boolean isOwner = this.orderServiceToTest.isOwnerOrAdmin(userDetails, testId);

        Assertions.assertTrue(isOwner);
    }
}
