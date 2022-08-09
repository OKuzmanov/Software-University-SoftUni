package bg.softuni.PureWaterMiniCRM.models.serviceModels;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProductServiceModel extends BaseServiceModel{
    private int quantity;

    private ProductCategoryEnum type;

    private LocalDateTime productionDate;

    public ProductServiceModel() {
    }

    public ProductServiceModel(int quantity, ProductCategoryEnum type, LocalDateTime productionDate) {
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

    public LocalDateTime getProductionDate() {
        return productionDate;
    }

    public String getFormattedProductionDate() {
        return productionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void setProductionDate(LocalDateTime productionDate) {
        this.productionDate = productionDate;
    }

}
