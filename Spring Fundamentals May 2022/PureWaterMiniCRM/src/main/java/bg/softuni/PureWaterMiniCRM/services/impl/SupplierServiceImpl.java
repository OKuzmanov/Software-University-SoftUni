package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.SupplierServiceModel;
import bg.softuni.PureWaterMiniCRM.repositories.SupplierRepository;
import bg.softuni.PureWaterMiniCRM.services.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepo;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepo, ModelMapper modelMapper) {
        this.supplierRepo = supplierRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public SupplierServiceModel addSupplier(SupplierServiceModel supplierServiceModel) {

        Supplier supplierEntity = this.modelMapper.map(supplierServiceModel, Supplier.class);

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
}
