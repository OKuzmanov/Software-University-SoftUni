package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.SupplierServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.models.viewModels.SupplierViewModel;
import bg.softuni.PureWaterMiniCRM.repositories.SupplierRepository;
import bg.softuni.PureWaterMiniCRM.services.SupplierService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepo, UserService userService, ModelMapper modelMapper) {
        this.supplierRepo = supplierRepo;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public SupplierServiceModel addSupplier(SupplierServiceModel supplierServiceModel, PureWaterUserDetails userDetails) {

        Supplier supplierEntity = this.modelMapper.map(supplierServiceModel, Supplier.class);
        supplierEntity.setUserEntity(this.modelMapper.map(this.userService.findUserByUsername(userDetails.getUsername()), UserEntity.class));

        this.supplierRepo.save(supplierEntity);

        return this.modelMapper.map(supplierEntity, SupplierServiceModel.class);
    }

    @Override
    public SupplierServiceModel findByCompanyName(String companyName) {
        Optional<Supplier> supplierOpt = this.supplierRepo.findByCompanyName(companyName);

        return supplierOpt.isEmpty()
                ? null
                :this.modelMapper.map(supplierOpt.get(),SupplierServiceModel.class);
    }

    @Override
    public List<SupplierServiceModel> findAllSuppliers() {
        return this.supplierRepo
                .findAll()
                .stream()
                .map(s -> this.modelMapper.map(s, SupplierServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isRepoEmpty() {
        return this.supplierRepo.count() == 0;
    }

    @Override
    public void saveAll(List<Supplier> suppliers) {
        this.supplierRepo.saveAll(suppliers);
    }

    @Override
    public Supplier getRandomSupplier() {
        long randId = new Random().nextLong(1, 5);
        return this.supplierRepo.findById(randId).get();
    }

    @Override
    public List<SupplierViewModel> fetchAll() {
        List<Supplier> all = this.supplierRepo.findAll();
        return all
                .stream()
                .map(s -> this.modelMapper.map(s, SupplierViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public SupplierViewModel fetchById(Long id) {
        Optional<Supplier> supplierOpt = this.supplierRepo.findById(id);
        return supplierOpt.isEmpty()
                ? null
                : this.modelMapper.map(supplierOpt.get(), SupplierViewModel.class);
    }
}
