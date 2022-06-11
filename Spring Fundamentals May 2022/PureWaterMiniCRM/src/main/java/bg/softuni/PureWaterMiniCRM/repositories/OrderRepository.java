package bg.softuni.PureWaterMiniCRM.repositories;

import bg.softuni.PureWaterMiniCRM.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
