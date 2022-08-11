package bg.softuni.PureWaterMiniCRM.models.bindingModels;

import bg.softuni.PureWaterMiniCRM.models.entities.RawMaterial;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class SupplierAddBindingModel {
    @NotBlank(message = "Company name should not be blank!")
    @Size(min=3, max=25, message = "Company name should be between 3 and 25 characters long!")
    private String companyName;

    @NotBlank(message = "Email should not be blank!")
    @Email(message = "Email Should be a valid email address!")
    private String email;

    @NotBlank(message = "Phone should not be blank!")
    @Size(min=7, max=15, message = "Phone should be between 7 and 15 characters long!")
    private String phoneNumber;


    @NotBlank(message = "Address should not be blank!")
    @Size(min=3, max=25, message = "Address should be between 3 and 25 characters long!")
    private String address;

    @NotBlank(message = "Description should not be blank!")
    @Size(min=3, message = "Description should be at least 3 characters long!")
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
