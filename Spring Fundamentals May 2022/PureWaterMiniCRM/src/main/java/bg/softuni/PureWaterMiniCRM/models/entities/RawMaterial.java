package bg.softuni.PureWaterMiniCRM.models.entities;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="raw_materials")
public class RawMaterial extends BaseEntity{

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RawMaterialType type;

    @Column(name = "delivered_at", nullable = false)
    private LocalDateTime deliveredAt;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;


    public RawMaterial() {
    }

    public RawMaterial(int quantity, RawMaterialType type, LocalDateTime deliveredAt) {
        this.quantity = quantity;
        this.type = type;
        this.deliveredAt = deliveredAt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public RawMaterialType getType() {
        return type;
    }

    public void setType(RawMaterialType type) {
        this.type = type;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
