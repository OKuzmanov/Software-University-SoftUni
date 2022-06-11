package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.RawMaterial;
import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.RawMaterialServiceModel;
import bg.softuni.PureWaterMiniCRM.repositories.RawMaterialRepository;
import bg.softuni.PureWaterMiniCRM.services.RawMaterialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RawMaterialServiceImpl implements RawMaterialService {

    private final RawMaterialRepository rawMaterialRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public RawMaterialServiceImpl(RawMaterialRepository rawMaterialRepo, ModelMapper modelMapper) {
        this.rawMaterialRepo = rawMaterialRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public RawMaterialServiceModel addRawMaterials(RawMaterialServiceModel rawMaterialServiceModel) {

        RawMaterial entityToPersist = this.modelMapper.map(rawMaterialServiceModel, RawMaterial.class);

        this.rawMaterialRepo.save(entityToPersist);

        return this.modelMapper.map(entityToPersist, RawMaterialServiceModel.class);
    }
}
