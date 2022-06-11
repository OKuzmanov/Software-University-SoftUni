package bg.softuni.PureWaterMiniCRM.models.serviceModels;

import bg.softuni.PureWaterMiniCRM.models.entities.Supplier;
import bg.softuni.PureWaterMiniCRM.models.entities.User;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;

import java.time.LocalDateTime;

public class RawMaterialServiceModel extends BaseServiceModel{

    private int quantity;

    private RawMaterialType type;

    private LocalDateTime deliveredAt;

    private SupplierServiceModel supplier;

    private User user;

    public RawMaterialServiceModel() {
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

    public SupplierServiceModel getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierServiceModel supplier) {
        this.supplier = supplier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
