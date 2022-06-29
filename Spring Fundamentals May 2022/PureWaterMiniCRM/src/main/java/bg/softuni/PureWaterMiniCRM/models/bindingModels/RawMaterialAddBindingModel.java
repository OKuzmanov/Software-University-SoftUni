package bg.softuni.PureWaterMiniCRM.models.bindingModels;

import bg.softuni.PureWaterMiniCRM.models.entities.enums.RawMaterialType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class RawMaterialAddBindingModel {

    @Positive(message = "Quantity Should be a positive number!")
    private int quantity;

    @NotNull(message = "Raw material type should be selected!")
    private RawMaterialType type;

    @NotNull(message = "Date should be selected!")
    @PastOrPresent(message = "Delivery date should not be in the future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deliveredAt;

    @NotBlank(message = "Supplier name should be selected!")
    private String supplierName;

    public RawMaterialAddBindingModel() {
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
