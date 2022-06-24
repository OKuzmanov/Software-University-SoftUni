package bg.softuni.examprepshoppinglist.web;

import bg.softuni.examprepshoppinglist.models.bindingModels.UserLoginBindingModel;
import bg.softuni.examprepshoppinglist.models.bindingModels.UserRegisterBindingModel;
import bg.softuni.examprepshoppinglist.models.services.UserServiceModel;
import bg.softuni.examprepshoppinglist.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("userLoginBindingModel")
    public UserLoginBindingModel addUserLoginBindingModel() {
      return new UserLoginBindingModel();
    }

    @ModelAttribute("wrongCredentials")
    public boolean addWrongCredentials() {
        return false;
    }

    @GetMapping("/login")
    public String getLogin() {

        if (this.userService.getCurrentUser().getUsername() != null) {
            return "redirect:/home";
        }

        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@Valid UserLoginBindingModel userLoginBindingModel, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            return "redirect:/users/login";
        }

        boolean isValidCredentials = this.userService.chekIsValidCredentials(userLoginBindingModel.getUsername(), userLoginBindingModel.getPassword());
        if(!isValidCredentials) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("wrongCredentials", true);
            return "redirect:/users/login";
        }

        this.userService.logInUser(this.modelMapper.map(userLoginBindingModel, UserServiceModel.class));

        return "redirect:/home";
    }

    @ModelAttribute("isUsernameTaken")
    public boolean addIsUsernameTaken() {
        return false;
    }

    @ModelAttribute("isPasswordMatch")
    public boolean addIsPasswordMatch() {
        return true;
    }

    @ModelAttribute("userRegisterBindingModel")
    public UserRegisterBindingModel addUserRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
                redirectAttributes.addFlashAttribute("isPasswordMatch", false);
            }
            return "redirect:/users/register";
        }

        UserServiceModel usm = this.userService.findByUsername(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        if (usm != null) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("isUsernameTaken", true);
            return "redirect:/users/register";
        }

        this.userService.register(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        return "redirect:/users/login";
    }

    @GetMapping("/logout")
    public String getLogout() {
        if (this.userService.getCurrentUser().getUsername() == null) {
            return "redirect:/users/login";
        }
        this.userService.logout();
        return "index";
    }
}
