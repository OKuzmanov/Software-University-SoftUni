package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.RawMaterial;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.ProductServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.RawMaterialServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.models.viewModels.RawMaterialViewModel;

import java.util.List;

public interface RawMaterialService {
    RawMaterialServiceModel addRawMaterials(RawMaterialServiceModel rawMaterialServiceModel);

    boolean isRepoEmpty();

    void saveAll(List<RawMaterial> rawMaterials);

    List<RawMaterialViewModel> fetchAll();

    RawMaterialViewModel fetchById(Long id);

    int getQuantityByType(RawMaterialType type);

    RawMaterialServiceModel reduceQuantityBy(RawMaterialType type, int quantity);

    List<RawMaterialServiceModel> findAll();

    RawMaterialServiceModel findById(long id);

    RawMaterialServiceModel updateRawMaterial(Long id, RawMaterialServiceModel rawMaterialServiceModel);

    boolean isAdmin(PureWaterUserDetails userDetails);

    void deleteRawMaterial(long id);
}
