package bg.softuni.examprepmusicdb.services;

import bg.softuni.examprepmusicdb.models.entities.User;
import bg.softuni.examprepmusicdb.models.serviceModels.UserServiceModel;

public interface UserService {
    UserServiceModel findByUsername(String username);

    void userLogin(UserServiceModel userServiceModel);

    UserServiceModel findByEmail(String email);

    void userRegister(UserServiceModel userServiceModel);

    User fetchCurrentUser();

    void logout();

    boolean isCurrentUserLoggedIn();
}
