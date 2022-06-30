package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.RawMaterial;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.RawMaterialServiceModel;

import java.util.List;

public interface RawMaterialService {
    RawMaterialServiceModel addRawMaterials(RawMaterialServiceModel rawMaterialServiceModel);

    boolean isRepoEmpty();

    void saveAll(List<RawMaterial> rawMaterials);

}
