package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.entities.Order;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.models.viewModels.OrderViewModel;
import bg.softuni.PureWaterMiniCRM.repositories.OrderRepository;
import bg.softuni.PureWaterMiniCRM.services.CustomerService;
import bg.softuni.PureWaterMiniCRM.services.OrderService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public OrderServiceModel addOrder(OrderServiceModel orderServiceModel,
                                      PureWaterUserDetails userDetails) {

        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        orderEntity.setUser(this.modelMapper.map(this.userService.findUserByUsername(userDetails.getUsername()), UserEntity.class));
        orderEntity.setCustomer(this.modelMapper.map(orderServiceModel.getCustomer(), Customer.class));

        this.orderRepo.save(orderEntity);

        return this.modelMapper.map(orderEntity, OrderServiceModel.class);
    }

    @Override
    public List<OrderViewModel> fetchAll() {
        List<OrderViewModel> models = this.orderRepo.findAll()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());
        return models;
    }

    @Override
    public OrderViewModel fetchById(Long id) {
        Optional<Order> ordeOpt = this.orderRepo.findById(id);
        return ordeOpt.isEmpty()
                ? null
                : this.modelMapper.map(ordeOpt.get(), OrderViewModel.class);
    }

    @Override
    public Long addOrderViaRestApi(OrderServiceModel orderServiceModel) {
        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepo.save(orderEntity);

        return orderEntity.getId();
    }

    @Override
    public void deleteById(Long id) {
        this.orderRepo.deleteById(id);
    }

    @Override
    public OrderServiceModel updateOrder(Long id, OrderServiceModel orderServiceModel) {
        Optional<Order> orderOpt = this.orderRepo.findById(id);

        if (orderOpt.isEmpty()) {
            return null;
        } else {
            Order orderEntity = orderOpt.get();
            orderEntity.setName(orderServiceModel.getName());
            orderEntity.setQuantity(orderServiceModel.getQuantity());
            orderEntity.setTotalPrice(orderServiceModel.getTotalPrice());
            orderEntity.setProdCategory(orderServiceModel.getProdCategory());
            orderEntity.setDescription(orderServiceModel.getDescription());
            orderEntity.setCreatedAt(orderServiceModel.getCreatedAt());

            this.orderRepo.save(orderEntity);
            return this.modelMapper.map(orderEntity, OrderServiceModel.class);
        }
    }
}
