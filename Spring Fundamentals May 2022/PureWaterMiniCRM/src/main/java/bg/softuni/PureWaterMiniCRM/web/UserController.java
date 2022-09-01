package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.UserUpdateBindingModel;
import bg.softuni.PureWaterMiniCRM.models.entities.Role;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.models.user.PureWaterUserDetails;
import bg.softuni.PureWaterMiniCRM.services.RoleService;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    private final RoleService roleService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, RoleService roleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute(name = "isNotUpdateSuccess")
    public boolean addIsNotUpdateSuccess() {
        return false;
    }

    @ModelAttribute(name = "isUpdateSuccess")
    public boolean addIsUpdateSuccess() {
        return false;
    }

    @ModelAttribute(name = "isAuthPrincipal")
    public boolean addIsAuthenticationPrincipal() {
        return false;
    }

    @GetMapping("/profile")
    public String getPrincipalDetails(@AuthenticationPrincipal PureWaterUserDetails userDetails, Model model,
                                      Principal principal) {
        UserServiceModel usm = this.userService.findById(userDetails.getId());
        model.addAttribute("userServiceModel", usm);
        return "user-profile";
    }

    @GetMapping("/profile/{id}")
    public String getUserDetailsById(@PathVariable(name = "id") Long id, Model model) {
        UserServiceModel usm = this.userService.findById(id);
        model.addAttribute("userServiceModel", usm);
        return "user-profile";
    }

    @PreAuthorize("@userServiceImpl.isUserAuthorized(#userDetails, #id)")
    @PostMapping("/update/{id}")
    public String userUpdate(@PathVariable(name = "id") Long id,
                             @Valid UserUpdateBindingModel userUpdateBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal PureWaterUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("isNotUpdateSuccess", true);
            return "redirect:/users/profile/" + id;
        }

        UserServiceModel usm = this.modelMapper.map(userUpdateBindingModel, UserServiceModel.class);
        if(userUpdateBindingModel.getRoles() != null) {
            Set<Role> updatedRoles = userUpdateBindingModel
                    .getRoles()
                    .stream()
                    .map(e -> this.roleService.findByName(e).get())
                    .collect(Collectors.toSet());
            usm.setRole(updatedRoles);
        }

        UserServiceModel updatedUSM = this.userService.updateUserDetails(id, usm);

        redirectAttributes.addFlashAttribute("isUpdateSuccess", true);
        return "redirect:/users/profile/" + id;
    }

    @PreAuthorize("@userServiceImpl.isUserAuthorized(#userDetails, #id)")
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id,
                             @AuthenticationPrincipal PureWaterUserDetails userDetails,
                             HttpServletRequest request) throws ServletException {

        this.userService.deleteUser(id);

        if (id == userDetails.getId()) {
            request.logout();
            return "redirect:/";
        }

        return "redirect:/home";
    }
}
