package bg.softuni.examprepbattleships.services.impl;

import bg.softuni.examprepbattleships.models.entities.Category;
import bg.softuni.examprepbattleships.models.entities.Ship;
import bg.softuni.examprepbattleships.models.entities.User;
import bg.softuni.examprepbattleships.models.serviceModels.ShipServiceModel;
import bg.softuni.examprepbattleships.models.serviceModels.UserServiceModel;
import bg.softuni.examprepbattleships.repositories.CategoryRepository;
import bg.softuni.examprepbattleships.repositories.ShipRepository;
import bg.softuni.examprepbattleships.services.ShipService;
import bg.softuni.examprepbattleships.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipServiceImpl implements ShipService {
    private final ShipRepository shipRepo;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepo;
    private final UserService userService;

    @Autowired
    public ShipServiceImpl(ShipRepository shipRepo, ModelMapper modelMapper, CategoryRepository categoryRepo, UserService userService) {
        this.shipRepo = shipRepo;
        this.modelMapper = modelMapper;
        this.categoryRepo = categoryRepo;
        this.userService = userService;
    }

    @Override
    public void addShip(ShipServiceModel shipServiceModel) {
        Ship shipEntity = this.modelMapper.map(shipServiceModel, Ship.class);

        Optional<Category> categoryOptional = this.categoryRepo.findByName(shipServiceModel.getCategory());
        shipEntity.setCategory(categoryOptional.get());

        UserServiceModel userServiceModel = this.userService.getCurrentUser();
        shipEntity.setUser(this.modelMapper.map(userServiceModel, User.class));

        this.shipRepo.save(shipEntity);
    }

    @Override
    public List<ShipServiceModel> returnAllAttackerShips() {
        List<Ship> currUserShips = this.shipRepo.findAllByUserId(userService.getCurrentUser().getId());
        return currUserShips
                    .stream()
                    .map(s -> this.modelMapper.map(s, ShipServiceModel.class))
                    .collect(Collectors.toList());
    }

    @Override
    public List<ShipServiceModel> returnAllDefenderShips() {
        List<Ship> allButCurrUserShips = this.shipRepo.findAllByNotUserId(userService.getCurrentUser().getId());
        return allButCurrUserShips
                .stream()
                .map(s -> this.modelMapper.map(s, ShipServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShipServiceModel> returnAllShipsSorted() {
        List<Ship> allShipsSorted = this.shipRepo.findAllOrdered();
        return allShipsSorted
                .stream()
                .map(s -> this.modelMapper.map(s, ShipServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void fireServiceLogic(Long attackerShip, Long defenderShip) {

        Ship attackerShipEntity = this.shipRepo.findById(attackerShip).get();
        Ship defenderShipEntity = this.shipRepo.findById(defenderShip).get();

        defenderShipEntity.setHealth(defenderShipEntity.getHealth() - attackerShipEntity.getPower());

        if (defenderShipEntity.getHealth() <= 0) {
            this.shipRepo.delete(defenderShipEntity);
        } else {
            this.shipRepo.save(defenderShipEntity);
        }
    }

    @Override
    public ShipServiceModel findByName(String name) {
        Optional<Ship> shipOpt = this.shipRepo.findByName(name);
        return shipOpt.isEmpty()
                ? null
                : this.modelMapper.map(shipOpt.get(), ShipServiceModel.class);
    }
}
