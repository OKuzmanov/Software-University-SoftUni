package bg.softuni.PureWaterMiniCRM.models.serviceModels;

public class CustomerServiceModel extends BaseServiceModel {
    private String companyName;

    private String email;

    private String phoneNumber;

    private String address;

    private String description;

    private UserServiceModel user;

    public CustomerServiceModel() {
    }

    public CustomerServiceModel(String companyName, String email, String phoneNumber, String address, String description, UserServiceModel user) {
        this.companyName = companyName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = description;
        this.user = user;
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

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }
}
