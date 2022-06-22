package bg.softuni.examprepbattleships.services.impl;

import bg.softuni.examprepbattleships.models.entities.User;
import bg.softuni.examprepbattleships.models.serviceModels.UserServiceModel;
import bg.softuni.examprepbattleships.repositories.UserRepository;
import bg.softuni.examprepbattleships.security.CurrentUser;
import bg.softuni.examprepbattleships.services.UserService;
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
    public void loginUser(UserServiceModel userServiceModel) {
        this.currentUser.setUsername(userServiceModel.getUsername());
        this.currentUser.setFullName(userServiceModel.getFullName());
    }

    @Override
    public void logOut() {
        this.currentUser.setUsername(null);
        this.currentUser.setFullName(null);
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
        this.userRepo.save(modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public UserServiceModel getCurrentUser() {
        return this.currentUser.getUsername() == null
                ? null
                : this.modelMapper.map(userRepo.findByUsername(this.currentUser.getUsername()).get(), UserServiceModel.class);
    }

    @Override
    public void logout() {
        this.currentUser.setUsername(null);
        this.currentUser.setFullName(null);
    }
}
