package bg.softuni.PureWaterMiniCRM.web.rest;

import bg.softuni.PureWaterMiniCRM.models.serviceModels.UserServiceModel;
import bg.softuni.PureWaterMiniCRM.models.viewModels.OrderViewModel;
import bg.softuni.PureWaterMiniCRM.models.viewModels.UserViewModel;
import bg.softuni.PureWaterMiniCRM.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserRestController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public ResponseEntity<List<UserViewModel>> getAllUsers() {
       return ResponseEntity
                .ok(this.userService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserViewModel> getUserById(@PathVariable(name = "id") Long id) {

        UserServiceModel usm = this.userService.findById(id);

        if (usm == null) {
         return ResponseEntity
                 .notFound()
                 .build();
        } else {
            return ResponseEntity
                    .ok(this.modelMapper.map(usm, UserViewModel.class));
        }
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderViewModel>> getAllUserOrdersByID(@PathVariable(name = "id") Long id) {
        List<OrderViewModel> ordersById = this.userService.fetchAllOrdersByUserId(id);

        if (ordersById == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity
                    .ok(ordersById);
        }
    }
}
