package bg.softuni.PureWaterMiniCRM.services.impl;

import bg.softuni.PureWaterMiniCRM.models.entities.User;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.repositories.UserRepository;
import bg.softuni.PureWaterMiniCRM.security.CurrentUser;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, ModelMapper modelMapper, CurrentUser currentUser, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserServiceModel findUserByUsername(UserServiceModel userServiceModel) {
        User userEntity = userRepo.findByUsername(userServiceModel.getUsername()).orElse(null);
        return userEntity != null
                ? modelMapper.map(userEntity, UserServiceModel.class)
                : null;
    }

    @Override
    public UserServiceModel findUserByEmail(UserServiceModel userServiceModel) {
        User userEntity = userRepo.findByEmail(userServiceModel.getEmail()).orElse(null);
        return userEntity != null
                ? modelMapper.map(userEntity, UserServiceModel.class)
                : null;
    }

    @Override
    public void login(UserServiceModel userServiceModel) {
        this.currentUser.setUsername(userServiceModel.getUsername());
        this.currentUser.setFirstName(userServiceModel.getFirstName());
        this.currentUser.setLastName(userServiceModel.getLastName());
        this.currentUser.setLoggedIn(true);
    }

    @Override
    public void logout() {
        this.currentUser.clear();
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {

        this.modelMapper.addMappings(new PropertyMap<UserServiceModel, User>() {
            @Override
            protected void configure() {
                skip().setPassword(null);
            }
        });

        User entityToPersist = this.modelMapper.map(userServiceModel, User.class);
        entityToPersist.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));

        //TODO Add Roles

        return this.modelMapper.map(this.userRepo.save(entityToPersist), UserServiceModel.class);
    }

    @Override
    public User getCurrentUser() {
        Optional<User> optUser = this.userRepo.findByUsername(this.currentUser.getUsername());

        return optUser.get();
    }

    @Override
    public boolean isRepoEmpty() {
        return this.userRepo.count() == 0;
    }

    @Override
    public void saveAll(List<User> users) {
        this.userRepo.saveAll(users);
    }

    @Override
    public boolean isCurrentUserLoggedIn() {
        return this.currentUser.getIsLoggedIn();
    }
}
