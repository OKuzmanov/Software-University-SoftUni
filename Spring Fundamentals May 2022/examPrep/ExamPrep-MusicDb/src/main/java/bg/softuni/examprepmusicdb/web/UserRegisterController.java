package bg.softuni.examprepmusicdb.web;

import bg.softuni.examprepmusicdb.models.bindingModels.UsersRegisterBindingModel;
import bg.softuni.examprepmusicdb.models.serviceModels.UserServiceModel;
import bg.softuni.examprepmusicdb.services.UserService;
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
public class UserRegisterController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRegisterController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("usersRegisterBindingModel")
    public UsersRegisterBindingModel addUsersRegisterBindingModel() {
        return new UsersRegisterBindingModel();
    }

    @ModelAttribute("isUsernameTaken")
    public boolean addIsUsernameTaken() {
        return false;
    }

    @ModelAttribute("isEmailTaken")
    public boolean addIsEmailTaken() {
        return false;
    }

    @ModelAttribute("noPasswordMatch")
    public boolean addNoPasswordMatch() {
        return false;
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid UsersRegisterBindingModel usersRegisterBindingModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("usersRegisterBindingModel", usersRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.usersRegisterBindingModel", bindingResult);
            return "redirect:/users/register";
        }

        if(!usersRegisterBindingModel.getPassword().equals(usersRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("usersRegisterBindingModel", usersRegisterBindingModel);
            redirectAttributes.addFlashAttribute("noPasswordMatch", true);
            return "redirect:/users/register";
        }

        UserServiceModel usmCheckByUsername = this.userService.findByUsername(usersRegisterBindingModel.getUsername());
        if (usmCheckByUsername != null) {
            redirectAttributes.addFlashAttribute("usersRegisterBindingModel", usersRegisterBindingModel);
            redirectAttributes.addFlashAttribute("isUsernameTaken", true);
            return "redirect:/users/register";
        }

        UserServiceModel usmCheckByEmail = this.userService.findByEmail(usersRegisterBindingModel.getEmail());
        if(usmCheckByEmail != null) {
            redirectAttributes.addFlashAttribute("usersRegisterBindingModel", usersRegisterBindingModel);
            redirectAttributes.addFlashAttribute("isEmailTaken", true);
            return "redirect:/users/register";
        }

        this.userService.userRegister(this.modelMapper.map(usersRegisterBindingModel, UserServiceModel.class));

        return "redirect:/users/login";
    }
}
