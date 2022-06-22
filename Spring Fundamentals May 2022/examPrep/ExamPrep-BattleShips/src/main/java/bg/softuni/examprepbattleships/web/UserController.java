package bg.softuni.examprepbattleships.web;

import bg.softuni.examprepbattleships.models.binding.UserLoginBindingModel;
import bg.softuni.examprepbattleships.models.binding.UserRegisterBindingModel;
import bg.softuni.examprepbattleships.models.serviceModels.UserServiceModel;
import bg.softuni.examprepbattleships.services.UserService;
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

    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@Valid UserLoginBindingModel userLoginBindingModel, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            return "redirect:/users/login";
        }

        UserServiceModel usm = this.userService.findByUsername(userLoginBindingModel.getUsername());

        if (usm == null || !usm.getPassword().equals(userLoginBindingModel.getPassword())) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("isWrongCredentials", true);
            return "redirect:/users/login";
        }

        this.userService.loginUser(usm);

        return "redirect:/home";
    }

    @ModelAttribute("isWrongCredentials")
    public boolean addIsWrongCredentials() {
        return false;
    }

    @ModelAttribute("userLoginBindingModel")
    public UserLoginBindingModel addUserLoginBindingModel() {
        return new UserLoginBindingModel();
    }

    @GetMapping("/register")
    public String getRegister(){
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        boolean doPasswordsMatch = userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword());

        if (bindingResult.hasErrors() || !doPasswordsMatch) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            if (!doPasswordsMatch) {
                redirectAttributes.addFlashAttribute("isPasswordMatch", false);
            }
            return "redirect:/users/register";
        }

        UserServiceModel usmByUsername = this.userService.findByUsername(userRegisterBindingModel.getUsername());
        UserServiceModel usmByEmail = this.userService.findByEmail(userRegisterBindingModel.getEmail());

        if (usmByUsername != null) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("isUsernameTaken", true);
            return "redirect:/users/register";
        }

        if (usmByEmail != null) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("isEmailTaken", true);
            return "redirect:/users/register";
        }

        this.userService.registerUser(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        return "redirect:/users/login";
    }

    @GetMapping("/logout")
    public String getLogout() {

        if(this.userService.getCurrentUser() == null) {
            return "redirect:/users/login";
        }

        this.userService.logout();

        return "redirect:/";
    }

    @ModelAttribute("isPasswordMatch")
    public boolean addIsPassMatch() {
        return true;
    }

    @ModelAttribute("isEmailTaken")
    public boolean addIsEmailTaken() {
        return false;
    }

    @ModelAttribute("isUsernameTaken")
    public boolean addIsUsernameTaken() {
        return false;
    }

    @ModelAttribute("userRegisterBindingModel")
    public UserRegisterBindingModel addUserRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }
}
