package bg.softuni.finalexam.web;

import bg.softuni.finalexam.models.bindingModels.UserRegisterBindingModel;
import bg.softuni.finalexam.models.serviceModels.UserServiceModel;
import bg.softuni.finalexam.services.UserService;
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

    @ModelAttribute("isUsernameTaken")
    public boolean addisUsernameTaken() {
        return false;
    }

    @ModelAttribute("isEmailTaken")
    public boolean addisEmailTaken() {
        return false;
    }

    @ModelAttribute("arePasswordsMatch")
    public boolean addArePasswordsMatch() {
        return true;
    }

    @ModelAttribute("userRegisterBindingModel")
    public UserRegisterBindingModel addUserRegBindingModel() {
        return new UserRegisterBindingModel();
    }

    @GetMapping("/register")
    public String getRegister() {
        if (this.userService.getCurrentUser() != null) {
            return "redirect:/home";
        }
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (this.userService.getCurrentUser() != null) {
            return "redirect:/home";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:/users/register";
        }

        boolean arePasswordsMatch = userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword());
        if (!arePasswordsMatch) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("arePasswordsMatch", false);
            return "redirect:/users/register";
        }

        UserServiceModel usmCheckUsername = this.userService.findByUsername(userRegisterBindingModel.getUsername());
        UserServiceModel usmCheckEmail = this.userService.findByEmail(userRegisterBindingModel.getEmail());

        if (usmCheckUsername != null && usmCheckEmail != null) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("isUsernameTaken", true);
            redirectAttributes.addFlashAttribute("isEmailTaken", true);
            return "redirect:/users/register";
        } else if (usmCheckUsername != null) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("isUsernameTaken", true);
            return "redirect:/users/register";
        } else if (usmCheckEmail != null) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("isEmailTaken", true);
            return "redirect:/users/register";
        }

        this.userService.registerUser(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        return "redirect:/users/login";
    }
}
