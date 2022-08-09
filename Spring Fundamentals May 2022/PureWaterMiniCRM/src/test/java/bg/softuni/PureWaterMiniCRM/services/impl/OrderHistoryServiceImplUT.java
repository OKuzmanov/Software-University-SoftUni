package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.OrderHistory;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderHistoryServiceModel;
import bg.softuni.PureWaterMiniCRM.repositories.OrderHistoryRepository;
import bg.softuni.PureWaterMiniCRM.services.OrderHistoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderHistoryServiceImplUT {
    @Mock
    private OrderHistoryRepository mockOrderHistoryRepo;
    @Mock
    private ModelMapper mockModelMapper;

    private OrderHistoryServiceImpl serviceToTest;

    @BeforeEach
    public void setup() {
        this.serviceToTest = new OrderHistoryServiceImpl(mockOrderHistoryRepo, mockModelMapper);
    }

    @Test
    public void testOrderHistoryService_saveAll() {
        List<OrderHistory> listOfOrderHistory = List.of(new OrderHistory());
        when(mockOrderHistoryRepo.saveAll(listOfOrderHistory))
                .thenReturn(listOfOrderHistory);

        Assertions.assertDoesNotThrow(() -> this.serviceToTest.saveAll(listOfOrderHistory));
    }

    @Test
    public void testOrderHistoryService_getAll() {
        List<OrderHistory> listOfOrderHistory = List.of(new OrderHistory());

        when(mockOrderHistoryRepo.findAll())
                .thenReturn(listOfOrderHistory);

        OrderHistoryServiceModel testOHSM = new OrderHistoryServiceModel("TestName", ProductCategoryEnum.NINETEEN_LITRES.getPrice().multiply(BigDecimal.valueOf(111)), 111,
                ProductCategoryEnum.NINETEEN_LITRES, "testDesc", LocalDateTime.of(2022, Month.JULY, 29, 23, 59), null, null);

        when(mockModelMapper.map(any(OrderHistory.class), eq(OrderHistoryServiceModel.class)))
                .thenReturn(testOHSM);

        List<OrderHistoryServiceModel> ordersHistory = this.serviceToTest.getAll();

        Assertions.assertEquals(1, ordersHistory.size());
        Assertions.assertEquals(ordersHistory.get(0).getName(), testOHSM.getName());
        Assertions.assertEquals(ordersHistory.get(0).getQuantity(), testOHSM.getQuantity());
        Assertions.assertEquals(ordersHistory.get(0).getDescription(), testOHSM.getDescription());
        Assertions.assertEquals(ordersHistory.get(0).getTotalPrice(), testOHSM.getTotalPrice());
        Assertions.assertEquals(ordersHistory.get(0).getCompletionDate(), testOHSM.getCompletionDate());
        Assertions.assertEquals(ordersHistory.get(0).getProdCategory(), testOHSM.getProdCategory());
    }
}
