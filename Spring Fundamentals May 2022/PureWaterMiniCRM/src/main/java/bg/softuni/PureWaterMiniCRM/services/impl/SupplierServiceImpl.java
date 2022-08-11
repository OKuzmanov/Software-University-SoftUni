package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.exceptions.ApiObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.exceptions.ObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
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
                .filter(s -> !s.isDeleted())
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
                .filter(s -> !s.isDeleted())
                .map(s -> this.modelMapper.map(s, SupplierViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public SupplierViewModel fetchById(Long id) {
        Optional<Supplier> supplierOpt = this.supplierRepo.findById(id);

        SupplierViewModel svm = supplierOpt.map(s -> this.modelMapper.map(s, SupplierViewModel.class))
                .orElseThrow(() -> new ApiObjectNotFoundException(id, "Supplier"));

        return svm;
    }

    @Override
    public SupplierServiceModel findById(int id) {
        Supplier supplierSource = supplierRepo.findById(Long.valueOf(id)).orElseThrow(() -> new ObjectNotFoundException(Long.valueOf(id), "Supplier"));
        return this.modelMapper.map(supplierSource, SupplierServiceModel.class);
    }

    @Override
    public SupplierServiceModel updateSupplier(Long id, SupplierServiceModel supplierServiceModel) {
        Optional<Supplier> optSupplier = this.supplierRepo.findById(id);

        if (optSupplier.isEmpty()) {
            throw new ObjectNotFoundException(id, "Customer");
        }

        Supplier supplierEntity = optSupplier.get();

        supplierEntity.setCompanyName(supplierServiceModel.getCompanyName());
        supplierEntity.setEmail(supplierServiceModel.getEmail());
        supplierEntity.setPhoneNumber(supplierServiceModel.getPhoneNumber());
        supplierEntity.setAddress(supplierServiceModel.getAddress());
        supplierEntity.setDescription(supplierServiceModel.getDescription());

        Supplier savedEntity = this.supplierRepo.save(supplierEntity);

        return this.modelMapper.map(savedEntity, SupplierServiceModel.class);
    }

    @Override
    public boolean isOwnerOrAdmin(PureWaterUserDetails userDetails, Long supplierId) {
        Long userEntityId = this.supplierRepo
                .findById(supplierId)
                .orElseThrow(() -> new ObjectNotFoundException(supplierId, "Supplier"))
                .getUserEntity()
                .getId();

        if(userDetails.getId() == userEntityId) {
            return true;
        }

        boolean isAdmin = userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + RoleEnum.ADMIN));

        return isAdmin;
    }

    @Override
    public SupplierServiceModel getSupplierByCompanyName(String companyName) {
        Supplier supplierEntity = this.supplierRepo.findByCompanyName(companyName).orElseThrow(() -> new ObjectNotFoundException(null, "Supplier"));

        return this.modelMapper.map(supplierEntity, SupplierServiceModel.class);
    }

    @Override
    public boolean deleteSupplier(long id) {
        Supplier supplierEntity = this.supplierRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Supplier"));

        supplierEntity.setDeleted(true);

        this.supplierRepo.save(supplierEntity);

        return true;
    }
}
