package bg.softuni.examprepmusicdb.services.impl;

import bg.softuni.examprepmusicdb.models.entities.User;
import bg.softuni.examprepmusicdb.models.serviceModels.UserServiceModel;
import bg.softuni.examprepmusicdb.repositories.UserRepository;
import bg.softuni.examprepmusicdb.security.CurrentUser;
import bg.softuni.examprepmusicdb.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, ModelMapper modelMapper, CurrentUser currentUser) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }

    @Override
    public UserServiceModel findByUsername(String username) {
        Optional<User> userOpt = this.userRepo.findByUsername(username);
        return userOpt.isEmpty()
                ? null
                : this.modelMapper.map(userOpt.get(), UserServiceModel.class);
    }

    @Override
    public void userLogin(UserServiceModel userServiceModel) {
        this.currentUser.setUsername(userServiceModel.getUsername());
        this.currentUser.setFullName(userServiceModel.getFullName());
    }

    @Override
    public UserServiceModel findByEmail(String email) {
        Optional<User> userOpt = this.userRepo.findByEmail(email);
        return userOpt.isEmpty()
                ? null
                : this.modelMapper.map(userOpt.get(), UserServiceModel.class);
    }

    @Override
    public void userRegister(UserServiceModel userServiceModel) {
        User entityToSave = this.modelMapper.map(userServiceModel, User.class);

        this.userRepo.save(entityToSave);
    }

    @Override
    public User fetchCurrentUser() {
        Optional<User> optUser = this.userRepo.findByUsername(this.currentUser.getUsername());
        return optUser.isEmpty()
                ? null
                : optUser.get();
    }

    @Override
    public void logout() {
        this.currentUser.setUsername(null);
        this.currentUser.setFullName(null);
    }

    @Override
    public boolean isCurrentUserLoggedIn() {
        return this.currentUser.getUsername() == null;
    }
}
