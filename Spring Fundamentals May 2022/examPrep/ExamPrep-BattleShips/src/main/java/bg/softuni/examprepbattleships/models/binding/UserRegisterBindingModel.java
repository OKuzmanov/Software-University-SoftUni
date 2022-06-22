package bg.softuni.examprepbattleships.models.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {
    @NotEmpty
    @Size(min = 3, max = 10)
    private String username;
    @NotEmpty
    @Size(min = 5, max = 20)
    private String fullName;
    @NotEmpty
    @Size(min = 3)
    private String password;
    @NotEmpty
    @Size(min = 3)
    private String confirmPassword;
    @NotEmpty
    @Email
    private String email;

    public UserRegisterBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
