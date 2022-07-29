package bg.softuni.PureWaterMiniCRM.web.rest;

import bg.softuni.PureWaterMiniCRM.exceptions.ApiObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.viewModels.*;
import bg.softuni.PureWaterMiniCRM.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    @Autowired
    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public ResponseEntity<List<CustomerViewModel>> getAllCustomers() {
        return ResponseEntity
                .ok(this.customerService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerViewModel> getCustomerById(@PathVariable Long id) {

        CustomerViewModel cvm = this.customerService.fetchById(id);

        return ResponseEntity.ok(cvm);

    }
}
