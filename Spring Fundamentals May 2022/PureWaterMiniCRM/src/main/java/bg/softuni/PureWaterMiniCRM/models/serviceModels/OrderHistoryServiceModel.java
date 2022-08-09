package bg.softuni.PureWaterMiniCRM.models.serviceModels;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderHistoryServiceModel extends BaseServiceModel{
    private String name;

    private BigDecimal totalPrice;

    private int quantity;

    private ProductCategoryEnum prodCategory;

    private String description;

    private LocalDateTime completionDate;

    private UserServiceModel user;

    private CustomerServiceModel customer;

    public OrderHistoryServiceModel() {
    }

    public OrderHistoryServiceModel(String name, BigDecimal totalPrice, int quantity, ProductCategoryEnum prodCategory, String description,
                                    LocalDateTime completionDate, UserServiceModel user, CustomerServiceModel customer) {
        this.name = name;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.prodCategory = prodCategory;
        this.description = description;
        this.completionDate = completionDate;
        this.user = user;
        this.customer = customer;
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

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
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
