package bg.softuni.PureWaterMiniCRM.repositories;

import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.SupplierServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByCompanyName(String companyName);
}
