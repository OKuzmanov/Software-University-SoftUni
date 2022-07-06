package bg.softuni.PureWaterMiniCRM.models.viewModels;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;

import java.time.LocalDateTime;

public class RawMaterialViewModel {

    private Long id;

    private int quantity;

    private RawMaterialType type;

    private LocalDateTime deliveredAt;

    public RawMaterialViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
