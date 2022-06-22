package bg.softuni.examprepbattleships.web;

import bg.softuni.examprepbattleships.models.binding.AddShipBindingModel;
import bg.softuni.examprepbattleships.models.serviceModels.ShipServiceModel;
import bg.softuni.examprepbattleships.services.ShipService;
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
@RequestMapping("/ships")
public class ShipController {

    private final ShipService shipService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ShipController(ShipService shipService, UserService userService, ModelMapper modelMapper) {
        this.shipService = shipService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String getShips(){

        if(this.userService.getCurrentUser() == null) {
            return "redirect:/users/login";
        }

        return "ship-add";
    }

    @PostMapping ("/add")
    public String postShips(@Valid AddShipBindingModel addShipBindingModel, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addShipBindingModel", addShipBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addShipBindingModel", bindingResult);
            return "redirect:/ships/add";
        }

        ShipServiceModel ssm = this.shipService.findByName(addShipBindingModel.getName());

        if (ssm != null) {
            redirectAttributes.addFlashAttribute("addShipBindingModel", addShipBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addShipBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("isNameTaken", true);
            return "redirect:/ships/add";
        }

        this.shipService.addShip(this.modelMapper.map(addShipBindingModel, ShipServiceModel.class));

        return "home";
    }

    @ModelAttribute("addShipBindingModel")
    public AddShipBindingModel addAddShipBindingModel() {
        return new AddShipBindingModel();
    }

    @ModelAttribute("isNameTaken")
    public boolean addIsNameTaken() {
        return false;
    }
}
