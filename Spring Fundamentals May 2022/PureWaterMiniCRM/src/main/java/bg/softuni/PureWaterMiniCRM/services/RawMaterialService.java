package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.RawMaterial;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.RawMaterialServiceModel;
import bg.softuni.PureWaterMiniCRM.models.viewModels.RawMaterialViewModel;

import java.util.List;

public interface RawMaterialService {
    RawMaterialServiceModel addRawMaterials(RawMaterialServiceModel rawMaterialServiceModel);

    boolean isRepoEmpty();

    void saveAll(List<RawMaterial> rawMaterials);

    List<RawMaterialViewModel> fetchAll();

    RawMaterialViewModel fetchById(Long id);

    int fetchAllByType(RawMaterialType type);

    void reduceQuantityBy(RawMaterialType type, int quantity);
}
