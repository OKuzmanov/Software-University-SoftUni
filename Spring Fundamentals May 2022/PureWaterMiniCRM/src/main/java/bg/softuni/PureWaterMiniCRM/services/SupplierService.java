package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.SupplierServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.models.viewModels.SupplierViewModel;

import java.util.List;

public interface SupplierService {
    SupplierServiceModel addSupplier(SupplierServiceModel supplierServiceModel, PureWaterUserDetails userDetails);

    SupplierServiceModel findByCompanyName(String companyName);

    List<SupplierServiceModel> findAllSuppliers();

    boolean isRepoEmpty();

    void saveAll(List<Supplier> suppliers);

    Supplier getRandomSupplier();

    List<SupplierViewModel> fetchAll();

    SupplierViewModel fetchById(Long id);

    SupplierServiceModel findById(int id);

    SupplierServiceModel updateSupplier(Long id, SupplierServiceModel supplierServiceModel);

    boolean isOwnerOrAdmin(PureWaterUserDetails userDetails, Long supplierId);

    SupplierServiceModel getSupplierByCompanyName(String companyName);

    boolean deleteSupplier(long id);
}
