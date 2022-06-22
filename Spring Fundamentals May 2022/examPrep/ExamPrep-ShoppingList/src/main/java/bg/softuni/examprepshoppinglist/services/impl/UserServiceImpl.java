package bg.softuni.examprepshoppinglist.services.impl;

import bg.softuni.examprepshoppinglist.models.entities.User;
import bg.softuni.examprepshoppinglist.models.services.UserServiceModel;
import bg.softuni.examprepshoppinglist.repositories.UserRepository;
import bg.softuni.examprepshoppinglist.security.CurrentUser;
import bg.softuni.examprepshoppinglist.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final CurrentUser currentUser;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, CurrentUser currentUser, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.currentUser = currentUser;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean chekIsValidCredentials(String username, String password) {
        return this.userRepo.findByUsernameAndPassword(username, password).isPresent();
    }

    @Override
    public void logInUser(UserServiceModel userServiceModel) {
        this.currentUser.setUsername(userServiceModel.getUsername());
    }

    @Override
    public boolean register(UserServiceModel userServiceModel) {

        User savedEntity = this.userRepo.save(this.modelMapper.map(userServiceModel, User.class));

        return savedEntity.getId() != null;
    }

    @Override
    public UserServiceModel findByUsername(UserServiceModel userServiceModel) {
        Optional<User> usernameOpt = this.userRepo.findByUsername(userServiceModel.getUsername());
        return usernameOpt.isEmpty()
                ? null
                : this.modelMapper.map(usernameOpt.get(), UserServiceModel.class);
    }

    @Override
    public void logout() {
        this.currentUser.setUsername(null);
    }
}
