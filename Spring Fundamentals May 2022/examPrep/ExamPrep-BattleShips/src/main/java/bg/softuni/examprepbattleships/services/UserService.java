package bg.softuni.examprepbattleships.services;

import bg.softuni.examprepbattleships.models.serviceModels.UserServiceModel;

public interface UserService {
    UserServiceModel findByUsername(String username);

    void loginUser(UserServiceModel userServiceModel);
    void logOut();

    UserServiceModel findByEmail(String email);

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel getCurrentUser();

    void logout();
}
