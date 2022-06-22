package bg.softuni.examprepbattleships.web;

import bg.softuni.examprepbattleships.models.serviceModels.ShipServiceModel;
import bg.softuni.examprepbattleships.services.ShipService;
import bg.softuni.examprepbattleships.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final ShipService shipService;
    private final UserService userService;

    @Autowired
    public HomeController(ShipService shipService, UserService userService) {
        this.shipService = shipService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/home")
    public String getHome(Model model){

        if(this.userService.getCurrentUser() == null) {
            return "redirect:/users/login";
        }

        List<ShipServiceModel> allShips = this.shipService.returnAllShipsSorted();
        List<ShipServiceModel> allAttackerShips = this.shipService.returnAllAttackerShips();
        List<ShipServiceModel> allDefenderShips = this.shipService.returnAllDefenderShips();

        model.addAttribute("attackerShips", allAttackerShips);
        model.addAttribute("defenderShips", allDefenderShips);
        model.addAttribute("allShips", allShips);

        return "home";
    }

    @PostMapping("/home")
    public String postHome(@RequestParam Long attackerShip, @RequestParam Long defenderShip){

        this.shipService.fireServiceLogic(attackerShip, defenderShip);

        return "redirect:/home";
    }
//
//    @ModelAttribute("attackerShips")
//    public List<ShipServiceModel> addAttackerShips() {
//        return new ArrayList<>();
//    }
}
