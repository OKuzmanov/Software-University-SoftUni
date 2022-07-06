package bg.softuni.PureWaterMiniCRM.services;

import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.models.viewModels.OrderViewModel;
import bg.softuni.PureWaterMiniCRM.models.viewModels.UserViewModel;

import java.util.List;

public interface UserService {

    public UserServiceModel findUserByUsername(String username);

    public UserServiceModel findUserByEmail(UserServiceModel userServiceModel);

    // Both methods obsolete due to Spring Boot Security
//    public void login(UserServiceModel userServiceModel);
//
//    public void logout();

    UserServiceModel register(UserServiceModel usm);

    boolean isRepoEmpty();

    void saveAll(List<UserEntity> userEntities);

    UserServiceModel findById(long id);

    List<UserViewModel> fetchAll();

    List<OrderViewModel> fetchAllOrdersByUserId(Long id);
}
