package bg.softuni.PureWaterMiniCRM.models.bindingModels;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginBindingModel {
    @NotEmpty(message = "Username must not be empty.")
    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 symbols long.")
    private String username;

    @NotEmpty(message = "Password must not be empty.")
    @Size(min = 5, message = "Password must be at least 5 symbols long.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
