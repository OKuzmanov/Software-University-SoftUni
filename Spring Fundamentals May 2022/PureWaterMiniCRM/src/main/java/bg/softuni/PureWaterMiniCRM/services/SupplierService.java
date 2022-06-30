package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.SupplierServiceModel;

import java.util.List;

public interface SupplierService {
    SupplierServiceModel addSupplier(SupplierServiceModel supplierServiceModel);

    SupplierServiceModel findByCompanyName(String companyName);

    List<SupplierServiceModel> findAllSuppliers();

    boolean isRepoEmpty();

    void saveAll(List<Supplier> suppliers);

    Supplier getRandomSupplier();
}
