package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.UserLoginBindingModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLoginController(UserService userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
    public String addUsernameValue() {
        return "";
    }

    @ModelAttribute("wrongCredentials")
    public boolean addUserLoginCredentialsAtt() {
        return false;
    }

    @GetMapping("/login")
    public String getLogin(@AuthenticationPrincipal PureWaterUserDetails userDetails) {
        if(userDetails != null) {
            return "redirect:/home";
        }
        return "login";
    }

    //Obsolete due to Spring Boot Security Login
//    @PostMapping("/login")
//    public String postLogin(@Valid UserLoginBindingModel userLoginBindingModel, BindingResult bindingResult,
//                            RedirectAttributes redirectAttributes) {
//
//        if(this.userService.isCurrentUserLoggedIn()) {
//            return "redirect:/home";
//        }
//
//        if(bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
//
//            return "redirect:/users/login";
//        }
//
//        UserServiceModel usm = modelMapper.map(userLoginBindingModel, UserServiceModel.class);
//
//        UserServiceModel existingUser = userService.findUserByUsername(usm);
//
//        if (existingUser == null || !passwordEncoder.matches(userLoginBindingModel.getPassword(), existingUser.getPassword())) {
//            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
//            redirectAttributes.addFlashAttribute("wrongCredentials", true);
//            return "redirect:/users/login";
//        }
//
//        userService.login(existingUser);
//
//        return "redirect:/home";
//    }

    @PostMapping("/login-error")
    public String getLoginErr(String username, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("wrongCredentials", true);

        return "redirect:/users/login";
    }

    //Obsolete due to Spring Boot Security Logout
//    @GetMapping("/logout")
//    public String getLogout() {
//        if(!this.userService.isCurrentUserLoggedIn()) {
//            return "redirect:/users/login";
//        }
//
//        this.userService.logout();
//
//        return "redirect:/";
//    }
}
