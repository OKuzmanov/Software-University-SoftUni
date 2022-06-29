package bg.softuni.PureWaterMiniCRM.models.bindingModels;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class ProductAddBindingModel {

    @Positive(message = "Quantity Should be a positive number!")
    private int quantity;


    @NotNull(message = "Product category should be selected!")
    private ProductCategoryEnum type;

    @NotNull(message = "Date should be selected!")
    @PastOrPresent(message = "Production date should not be in the future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime productionDate;

    public ProductAddBindingModel() {
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
