package bg.softuni.PureWaterMiniCRM.models.viewModels;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderViewModel {

    private Long id;
    private String name;

    private BigDecimal totalPrice;

    private int quantity;

    private ProductCategoryEnum prodCategory;

    private String description;

    private LocalDateTime createdAt;

    private UserViewModel user;

    private CustomerViewModel customer;

    public OrderViewModel() {
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

    public UserViewModel getUser() {
        return user;
    }

    public void setUser(UserViewModel user) {
        this.user = user;
    }

    public CustomerViewModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerViewModel customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
