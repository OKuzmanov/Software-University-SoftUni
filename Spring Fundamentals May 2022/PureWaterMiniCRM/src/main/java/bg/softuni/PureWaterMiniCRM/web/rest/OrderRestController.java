package bg.softuni.PureWaterMiniCRM.web.rest;

import bg.softuni.PureWaterMiniCRM.models.bindingModels.OrderAddBindingModelRest;
import bg.softuni.PureWaterMiniCRM.models.entities.Order;
import bg.softuni.PureWaterMiniCRM.models.serviceModels.OrderServiceModel;
import bg.softuni.PureWaterMiniCRM.models.viewModels.*;
import bg.softuni.PureWaterMiniCRM.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderRestController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public ResponseEntity<List<OrderViewModel>> getAllOrders() {
       return ResponseEntity
               .ok(this.orderService.fetchAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderViewModel> getOrderById(@PathVariable Long id) {

        OrderViewModel ovm = this.orderService.fetchById(id);

        if(ovm == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity
                    .ok(ovm);
        }
    }

    //Most Probably doesn't work due to Spring boot security CSRF restrictions
    @PostMapping()
    public ResponseEntity<OrderViewModel> createOrder(@Valid @RequestBody OrderAddBindingModelRest orderAddBindingModelRest,
                                                      UriComponentsBuilder uriComponentsBuilder) {

        Long newOrderId = this.orderService.addOrderViaRestApi(this.modelMapper.map(orderAddBindingModelRest, OrderServiceModel.class));

        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/orders/{id}")
                        .build(newOrderId))
                .build();
    }

    //Most Probably doesn't work due to Spring boot security CSRF restrictions
    @PatchMapping("/{id}")
    public ResponseEntity<OrderViewModel> updateOrderById(@PathVariable(name = "id") Long id, @Valid @RequestBody OrderAddBindingModelRest orderAddBindingModelRest) {
        OrderServiceModel osm = this.orderService.updateOrder(id, this.modelMapper.map(orderAddBindingModelRest, OrderServiceModel.class));

        if(osm == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(this.modelMapper.map(osm, OrderViewModel.class));
    }

    //Most Probably doesn't work due to Spring boot security CSRF restrictions
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderViewModel> deleteOrderById(@PathVariable(name = "id") Long id) {
        this.orderService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
