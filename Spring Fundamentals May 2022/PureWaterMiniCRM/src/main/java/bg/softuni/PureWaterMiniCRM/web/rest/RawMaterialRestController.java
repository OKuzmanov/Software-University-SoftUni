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
@RequestMapping("/api/raw/materials")
public class RawMaterialRestController {

    private final RawMaterialService rawMaterialService;

    @Autowired
    public RawMaterialRestController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping()
    public ResponseEntity<List<RawMaterialViewModel>> getAllRawMaterials() {
        return ResponseEntity
                .ok(this.rawMaterialService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialViewModel> getRawMaterialById(@PathVariable Long id) {

        RawMaterialViewModel rmvm = this.rawMaterialService.fetchById(id);

        return ResponseEntity.ok(rmvm);
    }
}
