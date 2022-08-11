package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.OrderHistory;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderHistoryServiceModel;
import bg.softuni.PureWaterMiniCRM.repositories.OrderHistoryRepository;
import bg.softuni.PureWaterMiniCRM.services.OrderHistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepo;
    private final ModelMapper modelMapper;

    public OrderHistoryServiceImpl(OrderHistoryRepository orderHistoryRepo, ModelMapper modelMapper) {
        this.orderHistoryRepo = orderHistoryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveAll(List<OrderHistory> orders) {
        this.orderHistoryRepo.saveAll(orders);
    }

    @Override
    public List<OrderHistoryServiceModel> getAll() {
        return this.orderHistoryRepo
                .findAll()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderHistoryServiceModel.class))
                .toList();
    }

    @Override
    public boolean isRepoEmpty() {

        return this.orderHistoryRepo.count() == 0;
    }
}
