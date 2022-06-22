package bg.softuni.examprepshoppinglist.services;

import bg.softuni.examprepshoppinglist.models.services.UserServiceModel;

public interface UserService {
    boolean chekIsValidCredentials(String username, String password);

    void logInUser(UserServiceModel userServiceModel);

    boolean register(UserServiceModel userServiceModel);

    UserServiceModel findByUsername(UserServiceModel userServiceModel);

    void logout();
}
