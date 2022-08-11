package bg.softuni.PureWaterMiniCRM.models.entities;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_history")
public class OrderHistory extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_category", nullable = false)
    private ProductCategoryEnum prodCategory;

    private String description;

    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completionDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public OrderHistory() {
    }

    public OrderHistory(String name, int quantity, ProductCategoryEnum prodCategory, String description, LocalDateTime completionDate, UserEntity userEntity, Customer customer) {
        this.name = name;
        this.quantity = quantity;
        this.prodCategory = prodCategory;
        this.description = description;
        this.completionDate = completionDate;
        this.userEntity = userEntity;
        this.customer = customer;
    }

    public OrderHistory(String name, BigDecimal totalPrice, int quantity, ProductCategoryEnum prodCategory, LocalDateTime completionDate, UserEntity userEntity, Customer customer) {
        this.name = name;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.prodCategory = prodCategory;
        this.completionDate = completionDate;
        this.userEntity = userEntity;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
