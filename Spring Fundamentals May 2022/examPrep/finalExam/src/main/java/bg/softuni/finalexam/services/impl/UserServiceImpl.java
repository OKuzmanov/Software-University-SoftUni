package bg.softuni.finalexam.services.impl;

import bg.softuni.finalexam.models.entities.User;
import bg.softuni.finalexam.models.serviceModels.UserServiceModel;
import bg.softuni.finalexam.models.viewModels.SongViewModel;
import bg.softuni.finalexam.repositories.UserRepository;
import bg.softuni.finalexam.security.CurrentUser;
import bg.softuni.finalexam.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void loginUser(UserServiceModel userServiceModel) {
        this.currentUser.setUsername(userServiceModel.getUsername());
        this.currentUser.setEmail(userServiceModel.getEmail());
    }

    @Override
    public UserServiceModel findByEmail(String email) {
        Optional<User> userOpt = this.userRepo.findByEmail(email);
        return userOpt.isEmpty()
                ? null
                : this.modelMapper.map(userOpt.get(), UserServiceModel.class);
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        User entityToBeSaved = this.modelMapper.map(userServiceModel, User.class);
        this.userRepo.save(entityToBeSaved);
    }

    @Override
    public User getCurrentUser() {
        return this.currentUser.getUsername() == null
                ? null
                : this.userRepo.findByUsername(this.currentUser.getUsername()).get();
    }

    @Override
    public void updateUser(User currentUser) {
        this.userRepo.save(currentUser);
    }

    @Override
    public void logoutUser() {
        this.currentUser.setUsername(null);
        this.currentUser.setEmail(null);
    }
}
