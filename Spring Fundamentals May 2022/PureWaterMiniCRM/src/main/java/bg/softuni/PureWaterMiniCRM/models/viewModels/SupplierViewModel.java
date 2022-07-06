package bg.softuni.PureWaterMiniCRM.models.viewModels;

import bg.softuni.PureWaterMiniCRM.models.entities.RawMaterial;

import java.util.List;

public class SupplierViewModel {

    private Long id;

    private String companyName;

    private String email;

    private String phoneNumber;

    private String address;

    private String description;

    private List<RawMaterialViewModel> rawMaterials;

    public SupplierViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<RawMaterialViewModel> getRawMaterials() {
        return rawMaterials;
    }

    public void setRawMaterials(List<RawMaterialViewModel> rawMaterials) {
        this.rawMaterials = rawMaterials;
    }
}
