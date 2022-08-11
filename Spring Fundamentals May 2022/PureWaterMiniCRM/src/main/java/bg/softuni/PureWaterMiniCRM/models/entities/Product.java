package bg.softuni.PureWaterMiniCRM.models.entities;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ProductCategoryEnum type;

    @Column(name = "production_date")
    private LocalDateTime productionDate;

    public Product() {
    }

    public Product(int quantity, ProductCategoryEnum type) {
        this.quantity = quantity;
    }

    public Product(int quantity, ProductCategoryEnum type, LocalDateTime productionDate) {
        this.quantity = quantity;
        this.type = type;
        this.productionDate = productionDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductCategoryEnum getType() {
        return type;
    }

    public void setType(ProductCategoryEnum type) {
        this.type = type;
    }

    public LocalDateTime getproductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDateTime productionDate) {
        this.productionDate = productionDate;
    }


}
