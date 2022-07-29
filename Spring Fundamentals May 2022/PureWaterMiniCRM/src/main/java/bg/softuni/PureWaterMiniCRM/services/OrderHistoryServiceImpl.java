package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.OrderHistory;
import bg.softuni.PureWaterMiniCRM.repositories.OrderHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepo;

    public OrderHistoryServiceImpl(OrderHistoryRepository orderHistoryRepo) {
        this.orderHistoryRepo = orderHistoryRepo;
    }

    @Override
    public void saveAll(List<OrderHistory> orders) {
        this.orderHistoryRepo.saveAll(orders);
    }
}
