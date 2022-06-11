package bg.softuni.PureWaterMiniCRM.models.entities;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.ProductCategoryEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(name="total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "product_category", nullable = false)
    private ProductCategoryEnum prodCategory;

    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    public Order() {
        //TODO Replace '1' with the actual quantity in a pallet
//        switch(prodCategory.name()){
//            case "HALF_LITRE":
//                this.totalPrice = prodCategory.getPrice().multiply(BigDecimal.valueOf(1));
//                break;
//            case "LITRE_AND_HALF":
//                this.totalPrice = prodCategory.getPrice().multiply(BigDecimal.valueOf(1));
//                break;
//            case "TEN_LITRES":
//                this.totalPrice = prodCategory.getPrice().multiply(BigDecimal.valueOf(1));
//                break;
//            case "NINETEEN_LITRES":
//                this.totalPrice = prodCategory.getPrice().multiply(BigDecimal.valueOf(1));
//                break;
//        }
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

    public void setQuantity(int quantityPallet) {
        this.quantity = quantityPallet;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
