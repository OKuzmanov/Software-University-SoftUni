package bg.softuni.examprep_springfund.web;

import bg.softuni.examprep_springfund.model.binding.UserLoginBindingModel;
import bg.softuni.examprep_springfund.model.binding.UserRegisterBindingModel;
import bg.softuni.examprep_springfund.model.service.UserServiceModel;
import bg.softuni.examprep_springfund.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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

    @GetMapping("/register")
    private String getRegister(Model model) {

        if(this.userService.isCurrUserLoggedIn()) {
            return "redirect:/home";
        }

        if (!model.containsAttribute("isAlreadyRegistered")) {
            model.addAttribute("isAlreadyRegistered", false);
        }

        return "register";
    }

    @PostMapping("/register")
    private String registerPost(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() ||
                !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {

            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:register";
        }

        if (this.userService.checkUserAlreadyRegistered(userRegisterBindingModel)) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("isAlreadyRegistered", true);

            return "redirect:register";
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class);

        userService.registerUser(userServiceModel);

        return "redirect:login";
    }

    @GetMapping("/login")
    private String getLogin(Model model) {

        if(this.userService.isCurrUserLoggedIn()) {
            return "redirect:/home";
        }

        if (!model.containsAttribute("isFound")) {
            model.addAttribute("isFound", true);
        }
        return "login";
    }

    @PostMapping("/login")
    private String loginPost(@Valid UserLoginBindingModel userLoginBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);

            return "redirect:login";
        }

        UserServiceModel userServiceModel = userService.findUser(this.modelMapper.map(userLoginBindingModel, UserServiceModel.class));

        if (userServiceModel == null) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("isFound", false);

            return "redirect:login";
        }

        userService.loginUser(userServiceModel.getId(), userServiceModel.getUsername());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();

        if(!this.userService.isCurrUserLoggedIn()) {
            return "redirect:/users/login";
        }

        return "redirect:/";
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    @ModelAttribute
    public UserLoginBindingModel userLoginBindingModel() {
        return new UserLoginBindingModel();
    }
}
