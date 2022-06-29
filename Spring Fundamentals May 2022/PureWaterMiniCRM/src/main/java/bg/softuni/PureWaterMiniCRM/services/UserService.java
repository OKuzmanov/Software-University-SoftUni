package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.User;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.security.CurrentUser;

import java.util.List;

public interface UserService {

    public UserServiceModel findUserByUsername(UserServiceModel userServiceModel);

    public UserServiceModel findUserByEmail(UserServiceModel userServiceModel);

    public void login(UserServiceModel userServiceModel);

    public void logout();

    UserServiceModel register(UserServiceModel usm);

    User getCurrentUser();

    boolean isRepoEmpty();

    void saveAll(List<User> users);

    boolean isCurrentUserLoggedIn();
}
