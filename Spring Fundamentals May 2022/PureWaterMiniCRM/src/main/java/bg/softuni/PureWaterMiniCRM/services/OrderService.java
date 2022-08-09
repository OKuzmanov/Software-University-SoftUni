package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.OrderAddBindingModel;
import bg.softuni.PureWaterMiniCRM.models.entities.Order;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.models.viewModels.OrderViewModel;

import java.util.List;

public interface OrderService {
    OrderServiceModel addOrder(OrderServiceModel orderServiceModel, PureWaterUserDetails userDetails);

    List<OrderViewModel> fetchAll();

    OrderViewModel fetchById(Long id);

    Long addOrderViaRestApi(OrderServiceModel orderServiceModel);

    boolean deleteByIdRest(Long id);

    boolean ifReadyDeleteById(Long id);

    OrderServiceModel updateOrderRest(Long id, OrderServiceModel orderServiceModel);

    OrderServiceModel updateOrder(Long id, OrderAddBindingModel orderAddBindingModel);

    boolean isRepoEmpty();

    void saveAll(List<Order> orders);

    int deleteOverDueOrders();

    int completeOrders();

    OrderServiceModel findById(Long id);

    boolean deleteById(Long id);

    boolean isOwnerOrAdmin(PureWaterUserDetails userDetails, Long orderId);
}
