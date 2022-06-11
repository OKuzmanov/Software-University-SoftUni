package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderServiceModel;

public interface OrderService {
    OrderServiceModel addOrder(OrderServiceModel orderServiceModel);
}
