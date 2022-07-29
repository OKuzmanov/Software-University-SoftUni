package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.exceptions.ApiObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.entities.Role;
import bg.softuni.PureWaterMiniCRM.models.entities.UserEntity;
import bg.softuni.PureWaterMiniCRM.models.entities.enums.RoleEnum;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.models.viewModels.OrderViewModel;
import bg.softuni.PureWaterMiniCRM.models.viewModels.UserViewModel;
import bg.softuni.PureWaterMiniCRM.models.viewModels.rest.UserViewModelRest;
import bg.softuni.PureWaterMiniCRM.repositories.UserRepository;
import bg.softuni.PureWaterMiniCRM.services.RoleService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, RoleService roleService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        UserEntity userEntity = userRepo.findByUsername(username).orElse(null);
        return userEntity != null
                ? modelMapper.map(userEntity, UserServiceModel.class)
                : null;
    }

    @Override
    public UserServiceModel findUserByEmail(UserServiceModel userServiceModel) {
        UserEntity userEntity = userRepo.findByEmail(userServiceModel.getEmail()).orElse(null);
        return userEntity != null
                ? modelMapper.map(userEntity, UserServiceModel.class)
                : null;
    }

    // Obsolete due to Spring Boot Security login.
//    @Override
//    public void login(UserServiceModel userServiceModel) {
//        this.currentUser.setUsername(userServiceModel.getUsername());
//        this.currentUser.setFirstName(userServiceModel.getFirstName());
//        this.currentUser.setLastName(userServiceModel.getLastName());
//        this.currentUser.setLoggedIn(true);
//    }

    //Obsolete due to Spring Boot Security Logout.
//    @Override
//    public void logout() {
//        this.currentUser.clear();
//    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {

        this.modelMapper.addMappings(new PropertyMap<UserServiceModel, UserEntity>() {
            @Override
            protected void configure() {
                skip().setPassword(null);
            }
        });

        UserEntity entityToPersist = this.modelMapper.map(userServiceModel, UserEntity.class);

        entityToPersist.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));

        Optional<Role> optRole = this.roleService.findByName(RoleEnum.USER);
        entityToPersist.setRole(Set.of(optRole.isEmpty()
                                        ? null
                                        : optRole.get()));

        return this.modelMapper.map(this.userRepo.save(entityToPersist), UserServiceModel.class);
    }

    @Override
    public boolean isRepoEmpty() {
        return this.userRepo.count() == 0;
    }

    @Override
    public void saveAll(List<UserEntity> userEntities) {
        this.userRepo.saveAll(userEntities);
    }

    @Override
    public UserServiceModel findById(long id) {
        Optional<UserEntity> userOpt = this.userRepo.findById(id);
        return userOpt.isEmpty()
                ? null
                : this.modelMapper.map(userOpt.get(), UserServiceModel.class);
    }

    @Override
    public List<UserViewModelRest> fetchAllRest() {
        return this.userRepo.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserViewModelRest.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserViewModel> fetchAll() {
        return this.userRepo.findAllSortedByOrders()
                .stream()
                .map(u -> this.modelMapper.map(u, UserViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderViewModel> fetchAllOrdersByUserId(Long id) {
        Optional<UserEntity> userOpt = this.userRepo.findById(id);

        if (userOpt.isEmpty()) {
            throw new ApiObjectNotFoundException(id, "User");
        } else {
            return userOpt.get()
                    .getOrders()
                    .stream()
                    .map(o -> this.modelMapper.map(o, OrderViewModel.class))
                    .collect(Collectors.toList());
        }
    }
}
