package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;

public interface UserService {

    public UserServiceModel findUserByUsername(UserServiceModel userServiceModel);

    public UserServiceModel findUserByEmail(UserServiceModel userServiceModel);

    public void login(UserServiceModel userServiceModel);

    public void logout();

    UserServiceModel register(UserServiceModel usm);
}
