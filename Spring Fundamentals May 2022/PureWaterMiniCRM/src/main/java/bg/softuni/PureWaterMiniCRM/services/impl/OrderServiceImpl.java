package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.exceptions.ApiObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.exceptions.ObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.bindingModels.OrderAddBindingModel;
import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.entities.Order;
import bg.softuni.PureWaterMiniCRM.models.entities.OrderHistory;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.CustomerServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.models.viewModels.OrderViewModel;
import bg.softuni.PureWaterMiniCRM.repositories.OrderRepository;
import bg.softuni.PureWaterMiniCRM.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final UserService userService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderHistoryService orderHistoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepo, UserService userService, CustomerService customerService, ProductService productService, OrderHistoryService orderHistoryService, ModelMapper modelMapper) {
        this.orderRepo = orderRepo;
        this.userService = userService;
        this.customerService = customerService;
        this.productService = productService;
        this.orderHistoryService = orderHistoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderServiceModel addOrder(OrderServiceModel orderServiceModel,
                                      PureWaterUserDetails userDetails) {

        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        orderEntity.setUser(this.modelMapper.map(this.userService.findUserByUsername(userDetails.getUsername()), UserEntity.class));
        orderEntity.setCustomer(this.modelMapper.map(orderServiceModel.getCustomer(), Customer.class));

        orderEntity.setTotalPrice(orderEntity.getProdCategory().getPrice().multiply(BigDecimal.valueOf(orderEntity.getQuantity())));

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

        OrderViewModel ovm = ordeOpt.map(o -> this.modelMapper.map(o, OrderViewModel.class))
                .orElseThrow(() -> new ApiObjectNotFoundException(id, "Order"));

        return ovm;
    }

    @Override
    public Long addOrderViaRestApi(OrderServiceModel orderServiceModel) {
        Order orderEntity = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepo.save(orderEntity);

        return orderEntity.getId();
    }

    @Override
    public boolean deleteByIdRest(Long id) {
        Optional<Order> orderOpt = this.orderRepo.findById(id);

        if (orderOpt.isEmpty()) {
            throw new ApiObjectNotFoundException(id, "Order");
        }

        return isDeleteSuccess(id, orderOpt);
    }

    @Override
    public boolean ifReadyDeleteById(Long id) {
        Optional<Order> orderOpt = this.orderRepo.findById(id);

        if (orderOpt.isEmpty()) {
            throw new ObjectNotFoundException(id, "Order");
        }

        OrderHistory newOrderHistory = new OrderHistory(orderOpt.get().getName(), orderOpt.get().getTotalPrice(), orderOpt.get().getQuantity(),
                orderOpt.get().getProdCategory(), LocalDateTime.now(), orderOpt.get().getUser(), orderOpt.get().getCustomer());
        newOrderHistory.setDescription("Order Completed.");

        this.orderHistoryService.saveAll(List.of(newOrderHistory));

        return isDeleteSuccess(id, orderOpt);
    }

    private boolean isDeleteSuccess(Long id, Optional<Order> orderOpt) {
        ProductCategoryEnum prodCategory = orderOpt.get().getProdCategory();
        Integer quantityProduced = this.productService.findQuantityProducedOfType(prodCategory);

        int orderQuantity = orderOpt.get().getQuantity();
        if (quantityProduced >= orderQuantity) {
            this.orderRepo.deleteById(id);
            this.productService.reduceQuantityBy(prodCategory, orderQuantity);
            return true;
        }

        return false;
    }

    @Override
    public OrderServiceModel updateOrderRest(Long id, OrderServiceModel orderServiceModel) {
        Optional<Order> orderOpt = this.orderRepo.findById(id);

        if (orderOpt.isEmpty()) {
            throw new ApiObjectNotFoundException(id, "Order");
        }
        Order orderEntity = orderOpt.get();
        orderEntity.setName(orderServiceModel.getName());
        orderEntity.setQuantity(orderServiceModel.getQuantity());
        orderEntity.setTotalPrice(orderServiceModel.getTotalPrice());
        orderEntity.setProdCategory(orderServiceModel.getProdCategory());
        orderEntity.setDescription(orderServiceModel.getDescription());
        orderEntity.setExpiryDate(orderServiceModel.getExpiryDate());

        this.orderRepo.save(orderEntity);
        return this.modelMapper.map(orderEntity, OrderServiceModel.class);

    }

    @Override
    public OrderServiceModel updateOrder(Long id, OrderAddBindingModel orderAddBindingModel) {
        Order orderEntity = orderRepo.findById(id).get();

        orderEntity.setName(orderAddBindingModel.getName());
        orderEntity.setQuantity(orderAddBindingModel.getQuantity());
        orderEntity.setProdCategory(orderAddBindingModel.getProdCategory());
        orderEntity.setTotalPrice(orderAddBindingModel.getProdCategory().getPrice().multiply(BigDecimal.valueOf(orderAddBindingModel.getQuantity())));
        orderEntity.setDescription(orderAddBindingModel.getDescription());
        orderEntity.setExpiryDate(orderAddBindingModel.getExpiryDate());
        orderEntity.setCustomer(this.modelMapper.map(customerService.findCustomerByCompanyName(orderAddBindingModel.getCustomer()), Customer.class));

        Order savedEntity = orderRepo.save(orderEntity);
        return this.modelMapper.map(savedEntity, OrderServiceModel.class);
    }

    @Override
    public boolean isRepoEmpty() {
        return this.orderRepo.count() == 0;
    }

    @Override
    public void saveAll(List<Order> orders) {
        this.orderRepo.saveAll(orders);
    }

    @Override
    public int deleteOverDueOrders() {
        List<Order> allOrders = this.orderRepo.findAll();

        List<Order> overDueOrders = new ArrayList<>();

        for (Order order : allOrders) {
            if (order.getExpiryDate().isBefore(LocalDateTime.now())) {
                overDueOrders.add(order);
            }
        }

        this.orderRepo.deleteAll(overDueOrders);

        return overDueOrders.size();
    }

    @Override
    public int completeOrders() {
        List<Order> allOrders = this.orderRepo.findAll();

        List<Order> listOrdersToDel = new ArrayList<>();
        List<OrderHistory> listOrderHistory = new ArrayList<>();

        for (Order order : allOrders) {
            if (order.getQuantity() <= productService.findQuantityProducedOfType(order.getProdCategory())) {

                listOrdersToDel.add(order);

                OrderHistory completedOrder = new OrderHistory(order.getName(), order.getTotalPrice(),
                        order.getQuantity(), order.getProdCategory(),
                        LocalDateTime.now(), order.getUser(), order.getCustomer());
                completedOrder.setDescription("Order Completed");
                listOrderHistory.add(completedOrder);

                this.productService.reduceQuantityBy(order.getProdCategory(), order.getQuantity());
            }
        }

        this.orderHistoryService.saveAll(listOrderHistory);

        this.orderRepo.deleteAll(listOrdersToDel);

        return listOrdersToDel.size();
    }

    @Override
    public OrderServiceModel findById(Long id) {
        return this.modelMapper.map(this.orderRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Order")), OrderServiceModel.class);
    }

    @Override
    public boolean deleteById(Long id) {
        this.orderRepo
                .delete(this.orderRepo.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(id, "Order")));
        return true;
    }

    @Override
    public boolean isOwnerOrAdmin(PureWaterUserDetails userDetails, Long orderId) {
        Long userEntityId = this.orderRepo
                .findById(orderId)
                .orElseThrow(() -> new ObjectNotFoundException(orderId, "Order"))
                .getUser()
                .getId();
        if(userDetails.getId() == userEntityId) {
            return true;
        }

        boolean isAdmin = userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + RoleEnum.ADMIN));

        return isAdmin;
    }
}
