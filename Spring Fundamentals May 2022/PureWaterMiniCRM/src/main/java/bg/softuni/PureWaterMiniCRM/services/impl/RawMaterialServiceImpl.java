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

        if (this.rawMaterialRepo.findAll().size() == 0) {
            this.databaseInit();
        }

        RawMaterial rawMaterialEntity = this.rawMaterialRepo.findByType(rawMaterialServiceModel.getType()).get();

        rawMaterialEntity.setQuantity(rawMaterialEntity.getQuantity() + rawMaterialServiceModel.getQuantity());
        rawMaterialEntity.setSupplier(this.modelMapper.map(rawMaterialServiceModel.getSupplier(), Supplier.class));
        rawMaterialEntity.setDeliveredAt(LocalDateTime.now());

        this.rawMaterialRepo.save(rawMaterialEntity);

        return this.modelMapper.map(rawMaterialEntity, RawMaterialServiceModel.class);
    }

    private void databaseInit() {
        RawMaterial r1 = new RawMaterial(0, RawMaterialType.BOTTLE_HALF_LITRE, LocalDateTime.now());
        RawMaterial r2 = new RawMaterial(0, RawMaterialType.BOTTLE_LITRE_AND_HALF, LocalDateTime.now());
        RawMaterial r3 = new RawMaterial(0, RawMaterialType.BOTTLE_TEN_LITRE, LocalDateTime.now());
        RawMaterial r4 = new RawMaterial(0, RawMaterialType.BOTTLE_NINETEEN_LITRE, LocalDateTime.now());
        RawMaterial r5 = new RawMaterial(0, RawMaterialType.BOTTLENECK, LocalDateTime.now());
        RawMaterial r6 = new RawMaterial(0, RawMaterialType.CAP_HALF_LITRE, LocalDateTime.now());
        RawMaterial r7 = new RawMaterial(0, RawMaterialType.CAP_LITRE_AND_HALF, LocalDateTime.now());
        RawMaterial r8 = new RawMaterial(0, RawMaterialType.CAP_NINETEEN_LITRE, LocalDateTime.now());
        RawMaterial r9 = new RawMaterial(0, RawMaterialType.CAP_TEN_LITRE, LocalDateTime.now());
        RawMaterial r10 = new RawMaterial(0, RawMaterialType.GLUE, LocalDateTime.now());
        RawMaterial r11 = new RawMaterial(0, RawMaterialType.HANDLE_TEN_LITRES, LocalDateTime.now());
        RawMaterial r12 = new RawMaterial(0, RawMaterialType.LABEL, LocalDateTime.now());

        this.rawMaterialRepo.saveAll(List.of(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12));
    }
}
