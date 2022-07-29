package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.OrderHistory;

import java.util.List;

public interface OrderHistoryService {
    void saveAll(List<OrderHistory> orders);
}
