package bg.softuni.PureWaterMiniCRM.models.viewModels;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;

import java.time.LocalDateTime;

public class ProductViewModel {
    private Long id;

    private int quantity;

    private ProductCategoryEnum type;

    private LocalDateTime productionDate;

    public ProductViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDateTime productionDate) {
        this.productionDate = productionDate;
    }
}
