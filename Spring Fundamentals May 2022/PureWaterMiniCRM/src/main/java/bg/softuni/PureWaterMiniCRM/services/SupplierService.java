package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.serviceModels.SupplierServiceModel;

import java.util.List;

public interface SupplierService {
    SupplierServiceModel addSupplier(SupplierServiceModel supplierServiceModel);

    SupplierServiceModel findByCompanyName(String companyName);

    List<SupplierServiceModel> findAllSuppliers();
}
