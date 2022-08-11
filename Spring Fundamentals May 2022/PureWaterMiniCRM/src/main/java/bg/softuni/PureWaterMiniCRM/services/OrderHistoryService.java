package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.OrderHistory;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderHistoryServiceModel;

import java.util.List;

public interface OrderHistoryService {
    void saveAll(List<OrderHistory> orders);

    List<OrderHistoryServiceModel> getAll();

    boolean isRepoEmpty();
}
