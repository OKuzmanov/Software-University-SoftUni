package bg.softuni.examprepbattleships.services;

import bg.softuni.examprepbattleships.models.serviceModels.ShipServiceModel;

import java.util.List;

public interface ShipService {
    void addShip(ShipServiceModel shipServiceModel);

    List<ShipServiceModel> returnAllAttackerShips();

    List<ShipServiceModel> returnAllDefenderShips();

    List<ShipServiceModel> returnAllShipsSorted();

    void fireServiceLogic(Long attackerShip, Long defenderShip);

    ShipServiceModel findByName(String name);
}
