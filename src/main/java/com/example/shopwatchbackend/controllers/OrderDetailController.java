package com.example.shopwatchbackend.controllers;

import com.example.shopwatchbackend.dtos.request.OrderDetailDTO;
import com.example.shopwatchbackend.dtos.response.OrderDetailResponse;
import com.example.shopwatchbackend.services.interfaces.IOrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order-details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final IOrderDetailService iOrderDetailService;

    @GetMapping("/order/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> getAllByOrderId(@PathVariable("id") int id) throws Exception {
        try{
            List<OrderDetailResponse> orderDetailResponseList = iOrderDetailService.getAllByOrderId(id);
            return ResponseEntity.ok().body(orderDetailResponseList);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/order/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> addOrderDetail(@PathVariable("id") int id,@Valid @RequestBody OrderDetailDTO orderDetailDTO) throws Exception {
        try{
            String result = iOrderDetailService.createOrderDetail(id,orderDetailDTO);
            return ResponseEntity.ok().body(result);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> updateOrderDetail(@PathVariable int id, @Valid @RequestBody OrderDetailDTO orderDetailDTO){
        String result = iOrderDetailService.updateOrderDetail(id,orderDetailDTO);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable int id){

        return ResponseEntity.ok("delete "+ id);
    }
}
