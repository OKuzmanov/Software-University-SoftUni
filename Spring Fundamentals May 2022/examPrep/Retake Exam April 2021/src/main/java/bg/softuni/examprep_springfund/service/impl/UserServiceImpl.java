package bg.softuni.examprep_springfund.service.impl;

import bg.softuni.examprep_springfund.model.binding.UserRegisterBindingModel;
import bg.softuni.examprep_springfund.model.entity.User;
import bg.softuni.examprep_springfund.model.service.UserServiceModel;
import bg.softuni.examprep_springfund.model.view.UserViewModel;
import bg.softuni.examprep_springfund.repository.UserRepository;
import bg.softuni.examprep_springfund.security.CurrentUser;
import bg.softuni.examprep_springfund.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void registerUser(UserServiceModel userServiceModel) {

        User userEntity = this.modelMapper.map(userServiceModel, User.class);

        userRepo.save(userEntity);
    }

    @Override
    public UserServiceModel findUser(UserServiceModel userServiceModel) {

        return this.userRepo.findByUsernameAndPassword(userServiceModel.getUsername(), userServiceModel.getPassword())
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public void loginUser(Long id, String username) {
        currentUser.setId(id);
        currentUser.setUsername(username);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public List<UserViewModel> findAllUsersOrderedByNumberOfOrdersDesc() {

        List<User> allUsers = this.userRepo.findAllOrderedByOrdersCount();

        return allUsers
                .stream()
                .map(user -> this.modelMapper.map(user, UserViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkUserAlreadyRegistered(UserRegisterBindingModel userRegisterBindingModel) {
        Optional<User> optUser = this.userRepo
                .findByUsernameAndEmail(userRegisterBindingModel.getUsername(), userRegisterBindingModel.getEmail());

        return optUser.isPresent();
    }

    @Override
    public boolean isCurrUserLoggedIn() {
        return this.currentUser.getUsername() != null;
    }
}
