package bg.softuni.examprep_springfund.service;

import bg.softuni.examprep_springfund.model.binding.UserRegisterBindingModel;
import bg.softuni.examprep_springfund.model.entity.User;
import bg.softuni.examprep_springfund.model.service.UserServiceModel;
import bg.softuni.examprep_springfund.model.view.UserViewModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUser(UserServiceModel userServiceModel);

    void loginUser(Long id, String username);

    Optional<User> findUserById(Long id);

    List<UserViewModel> findAllUsersOrderedByNumberOfOrdersDesc();

    boolean checkUserAlreadyRegistered(UserRegisterBindingModel userRegisterBindingModel);

    boolean isCurrUserLoggedIn();
}
