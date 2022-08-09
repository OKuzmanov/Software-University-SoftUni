package bg.softuni.PureWaterMiniCRM.models.serviceModels;

import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RawMaterialServiceModel extends BaseServiceModel{

    private int quantity;

    private RawMaterialType type;

    private LocalDateTime deliveredAt;

    private SupplierServiceModel supplier;

    private UserEntity userEntity;

    public RawMaterialServiceModel() {
    }

    public RawMaterialServiceModel(int quantity, RawMaterialType type, LocalDateTime deliveredAt, SupplierServiceModel supplier, UserEntity userEntity) {
        this.quantity = quantity;
        this.type = type;
        this.deliveredAt = deliveredAt;
        this.supplier = supplier;
        this.userEntity = userEntity;
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

    public String getFormattedDeliveredAt() {
        return deliveredAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
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

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
