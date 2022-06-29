package bg.softuni.springMVCDemo.web;

import bg.softuni.springMVCDemo.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

  @GetMapping
  public String newUser() {
    return "newuser";
  }

  @PostMapping
  public String createUser(UserDTO userDTO, Model model) {

    System.out.println("Creating new user... " + userDTO);

    model.addAttribute("newUser", userDTO);

    return "usercreated";
  }

  @ModelAttribute("newUser")
  public UserDTO userDTO(){
    return new UserDTO();
  }
}
