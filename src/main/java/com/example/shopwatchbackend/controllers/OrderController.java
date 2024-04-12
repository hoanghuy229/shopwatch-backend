package com.example.shopwatchbackend.controllers;

import com.example.shopwatchbackend.dtos.request.OrderRequest;
import com.example.shopwatchbackend.models.Customer;
import com.example.shopwatchbackend.dtos.response.OrderResponse;
import com.example.shopwatchbackend.services.interfaces.ICustomerService;
import com.example.shopwatchbackend.services.interfaces.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService iOrderService;
    private final ICustomerService iCustomerService;


    @GetMapping("/admin/get-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAll(){

        return ResponseEntity.ok("get all");
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getOrdersByCustomerId(@RequestHeader("Authorization") String token){
        try{
            String extractToken = token.substring(7);
            Customer customer = iCustomerService.getUserDetailFromToken(extractToken);
            List<OrderResponse> orderResponses = iOrderService.getAllByCustomerId(customer.getCustomerId());
            return ResponseEntity.ok().body(orderResponses);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    };

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> addOrder(@Valid @RequestBody OrderRequest orderRequest) throws Exception {
        try{
            String result = iOrderService.createOrder(orderRequest);
            return ResponseEntity.ok(result);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateOrder(@PathVariable("id") int id, OrderRequest orderRequest) throws Exception {

        String result = iOrderService.updateOrder(id, orderRequest);

        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") int id){

        return ResponseEntity.ok("delete "+ id);
    }
}
