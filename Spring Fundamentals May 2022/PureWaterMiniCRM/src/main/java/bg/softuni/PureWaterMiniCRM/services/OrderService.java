package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.models.viewModels.OrderViewModel;

import java.util.List;

public interface OrderService {
    OrderServiceModel addOrder(OrderServiceModel orderServiceModel, PureWaterUserDetails userDetails);

    List<OrderViewModel> fetchAll();

    OrderViewModel fetchById(Long id);

    Long addOrderViaRestApi(OrderServiceModel orderServiceModel);

    void deleteById(Long id);

    OrderServiceModel updateOrder(Long id, OrderServiceModel orderServiceModel);
}
