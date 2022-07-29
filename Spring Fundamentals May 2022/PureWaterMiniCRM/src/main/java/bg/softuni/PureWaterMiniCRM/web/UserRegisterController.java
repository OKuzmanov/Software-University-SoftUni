package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.UserRegisterBindingModel;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @ModelAttribute("userRegisterBindingModel")
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    //Obsolete due to custom validation.
//    @ModelAttribute("isExists")
//    public boolean addExistingUserAtt() {
//        return false;
//    }

    @GetMapping("/register")
    public String getRegister(@AuthenticationPrincipal PureWaterUserDetails userDetails) {
        if(userDetails != null) {
            return "redirect:/home";
        }
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, @AuthenticationPrincipal PureWaterUserDetails userDetails) {
        if(userDetails != null) {
            return "redirect:/home";
        }

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:/users/register";
        }

        //Obsolete due to custom validations: UniqueUsername, UniqueEmail, FieldMatch
//        UserServiceModel usmCheckUsername = this.userService.findUserByUsername(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
//        UserServiceModel usmCheckEmail = this.userService.findUserByEmail(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
//
//        if(usmCheckUsername != null || usmCheckEmail != null) {
//            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
//            redirectAttributes.addFlashAttribute("isExists", true);
//
//            return "redirect:/users/register";
//        }

        UserServiceModel usm = this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class);

        UserServiceModel registeredUsm = this.userService.register(usm);

        return "redirect:/users/login";
    }
}
