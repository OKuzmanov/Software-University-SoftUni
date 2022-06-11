package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.entities.Order;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderServiceModel;
import bg.softuni.PureWaterMiniCRM.repositories.OrderRepository;
import bg.softuni.PureWaterMiniCRM.services.CustomerService;
import bg.softuni.PureWaterMiniCRM.services.OrderService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final UserService userService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepo, UserService userService, CustomerService customerService, ModelMapper modelMapper) {
        this.orderRepo = orderRepo;
        this.userService = userService;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderServiceModel addOrder(OrderServiceModel orderServiceModel) {

        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        orderEntity.setUser(this.userService.getCurrentUser());
        orderEntity.setCustomer(this.modelMapper.map(orderServiceModel.getCustomer(), Customer.class));

        this.orderRepo.save(orderEntity);

        return this.modelMapper.map(orderEntity, OrderServiceModel.class);
    }
}
