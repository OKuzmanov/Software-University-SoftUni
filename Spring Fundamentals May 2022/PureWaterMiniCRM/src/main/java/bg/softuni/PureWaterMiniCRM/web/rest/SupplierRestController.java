package bg.softuni.PureWaterMiniCRM.web.rest;

import bg.softuni.PureWaterMiniCRM.exceptions.ApiObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.viewModels.*;
import bg.softuni.PureWaterMiniCRM.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierRestController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierRestController(SupplierService supplierService) {
        this.supplierService = supplierService;

    }

    @GetMapping()
    public ResponseEntity<List<SupplierViewModel>> getAllSuppliers() {
        return ResponseEntity
                .ok(this.supplierService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierViewModel> getSuppliersById(@PathVariable Long id) {

        SupplierViewModel svm = this.supplierService.fetchById(id);


        return ResponseEntity.ok(svm);
    }
}
