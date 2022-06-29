package bg.softuni.PureWaterMiniCRM.models.bindingModels;

import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.entities.User;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.CustomerServiceModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderAddBindingModel {

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
    @PastOrPresent(message = "Created At date should not be in the future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    @NotBlank(message = "Customer name should be selected!")
    private String customer;

    public OrderAddBindingModel() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
