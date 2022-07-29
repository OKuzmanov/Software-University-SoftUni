package bg.softuni.PureWaterMiniCRM.models.bindingModels;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class OrderAddBindingModelRest {

    @NotBlank(message = "Name should not be blank!")
    @Size(min=2, max=20, message = "Name should be between 2 to 25 characters long!")
    private String name;

    @Positive(message = "Quantity Should be a positive number!")
    private int quantity;

    @NotNull(message = "Product category should be selected!")
    private ProductCategoryEnum prodCategory;

    @NotBlank(message = "Description should not be blank!")
    @Size(min=2, max=20, message = "Description should be between 2 to 20 characters long!")
    private String description;

    @NotNull(message = "Date should be selected!")
    @Future(message = "Expiry date should not be in the past!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime expiryDate;

    public OrderAddBindingModelRest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductCategoryEnum getProdCategory() {
        return prodCategory;
    }

    public void setProdCategory(ProductCategoryEnum prodCategory) {
        this.prodCategory = prodCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
