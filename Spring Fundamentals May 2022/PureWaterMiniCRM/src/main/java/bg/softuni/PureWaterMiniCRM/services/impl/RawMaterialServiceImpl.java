package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.RawMaterial;
import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.RawMaterialServiceModel;
import bg.softuni.PureWaterMiniCRM.repositories.RawMaterialRepository;
import bg.softuni.PureWaterMiniCRM.services.RawMaterialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

        RawMaterial rawMaterialEntity = this.rawMaterialRepo.findByType(rawMaterialServiceModel.getType()).get();

        rawMaterialEntity.setQuantity(rawMaterialEntity.getQuantity() + rawMaterialServiceModel.getQuantity());
        rawMaterialEntity.setSupplier(this.modelMapper.map(rawMaterialServiceModel.getSupplier(), Supplier.class));
        rawMaterialEntity.setDeliveredAt(LocalDateTime.now());

        this.rawMaterialRepo.save(rawMaterialEntity);

        return this.modelMapper.map(rawMaterialEntity, RawMaterialServiceModel.class);
    }

    @Override
    public boolean isRepoEmpty() {
        return this.rawMaterialRepo.count() == 0;
    }

    @Override
    public void saveAll(List<RawMaterial> rawMaterials) {
        this.rawMaterialRepo.saveAll(rawMaterials);
    }
}
