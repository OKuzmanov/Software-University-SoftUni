package bg.softuni.PureWaterMiniCRM.models.bindingModels;

import bg.softuni.PureWaterMiniCRM.models.entities.RawMaterial;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class SupplierAddBindingModel {
    @NotBlank
    @Size(min=3, max=25)
    private String companyName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min=7, max=15)
    private String phoneNumber;

    @NotBlank
    @Size(min=3, max=25)
    private String address;

    @NotBlank
    @Size(min=3, max=25)
    private String description;

    public SupplierAddBindingModel() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
