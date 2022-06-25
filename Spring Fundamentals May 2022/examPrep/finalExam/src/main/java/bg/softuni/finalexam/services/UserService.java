package bg.softuni.finalexam.services;

import bg.softuni.finalexam.models.entities.User;
import bg.softuni.finalexam.models.serviceModels.UserServiceModel;
import bg.softuni.finalexam.models.viewModels.SongViewModel;

import java.util.List;

public interface UserService {
    UserServiceModel findByUsername(String username);

    void loginUser(UserServiceModel userServiceModel);

    UserServiceModel findByEmail(String email);

    void registerUser(UserServiceModel userServiceModel);

    User getCurrentUser();

    void updateUser(User currentUser);

    void logoutUser();
}
