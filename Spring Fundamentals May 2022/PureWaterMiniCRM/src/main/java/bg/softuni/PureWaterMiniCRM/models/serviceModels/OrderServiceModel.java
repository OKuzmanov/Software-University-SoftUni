package bg.softuni.PureWaterMiniCRM.models.serviceModels;

import bg.softuni.PureWaterMiniCRM.models.entities.Customer;
import bg.softuni.PureWaterMiniCRM.models.entities.User;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderServiceModel extends BaseServiceModel{

    private String name;

    private BigDecimal totalPrice;

    private int quantity;

    private ProductCategoryEnum prodCategory;

    private String description;

    private LocalDateTime createdAt;

    private UserServiceModel user;

    private CustomerServiceModel customer;

    public OrderServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public CustomerServiceModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerServiceModel customer) {
        this.customer = customer;
    }
}
