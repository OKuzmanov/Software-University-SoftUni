package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.exceptions.ApiObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.exceptions.ObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.entities.RawMaterial;
import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.RawMaterialServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.models.viewModels.RawMaterialViewModel;
import bg.softuni.PureWaterMiniCRM.repositories.RawMaterialRepository;
import bg.softuni.PureWaterMiniCRM.services.RawMaterialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<RawMaterialViewModel> fetchAll() {
        List<RawMaterial> all = this.rawMaterialRepo.findAll();
        return all
                .stream()
                .map(rm -> this.modelMapper.map(rm, RawMaterialViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public RawMaterialViewModel fetchById(Long id) {
        Optional<RawMaterial> rawMaterialOpt = this.rawMaterialRepo.findById(id);

        RawMaterialViewModel rvm = rawMaterialOpt.map(rm -> this.modelMapper.map(rm, RawMaterialViewModel.class))
                .orElseThrow(() -> new ApiObjectNotFoundException(id, "Raw Material"));

        return rvm;
    }

    @Override
    public int getQuantityByType(RawMaterialType type) {
        Optional<RawMaterial> rawMaterialOpt = this.rawMaterialRepo.findByType(type);
        return rawMaterialOpt.isEmpty()
                ? 0
                : rawMaterialOpt.get().getQuantity();
    }

    @Override
    public RawMaterialServiceModel reduceQuantityBy(RawMaterialType type, int quantity) {
        Optional<RawMaterial> rawMaterialOpt = this.rawMaterialRepo.findByType(type);

        RawMaterial rawMaterialEntity = rawMaterialOpt.get();
        rawMaterialEntity.setQuantity(rawMaterialEntity.getQuantity() - quantity);

        this.rawMaterialRepo.save(rawMaterialEntity);

        return this.modelMapper.map(rawMaterialEntity, RawMaterialServiceModel.class);
    }

    @Override
    public List<RawMaterialServiceModel> findAll() {
        return this.rawMaterialRepo
                .findAll()
                .stream()
                .map(rm -> this.modelMapper.map(rm, RawMaterialServiceModel.class))
                .toList();
    }

    @Override
    public RawMaterialServiceModel findById(long id) {
        RawMaterial rawMaterialEntity = this.rawMaterialRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Raw Material"));

        return this.modelMapper.map(rawMaterialEntity, RawMaterialServiceModel.class);
    }

    @Override
    public RawMaterialServiceModel updateRawMaterial(Long id, RawMaterialServiceModel rawMaterialServiceModel) {
        RawMaterial rawMaterialEntity = this.rawMaterialRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Raw Material"));

        rawMaterialEntity.setQuantity(rawMaterialServiceModel.getQuantity());
        rawMaterialEntity.setDeliveredAt(rawMaterialServiceModel.getDeliveredAt());
        rawMaterialEntity.setSupplier(this.modelMapper.map(rawMaterialServiceModel.getSupplier(), Supplier.class));

        RawMaterial savedEntity = this.rawMaterialRepo.save(rawMaterialEntity);

        return this.modelMapper.map(savedEntity, RawMaterialServiceModel.class);
    }

    @Override
    public boolean isAdmin(PureWaterUserDetails userDetails) {
        return userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + RoleEnum.ADMIN));
    }

    @Override
    public void deleteRawMaterial(long id) {
        RawMaterial rawMaterialEntity = this.rawMaterialRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Raw Material"));

        this.rawMaterialRepo.delete(rawMaterialEntity);
    }
}
