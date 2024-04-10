package com.example.shopwatchbackend.controllers;

import com.example.shopwatchbackend.dtos.request.PaymentDTO;
import com.example.shopwatchbackend.dtos.response.PaymentResponse;
import com.example.shopwatchbackend.services.interfaces.IPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService iPaymentService;


    @GetMapping()
    public ResponseEntity<?> getAll(){
        List<PaymentResponse> paymentResponses = iPaymentService.getAll();
        return ResponseEntity.ok().body(paymentResponses);
    }
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addPayment(@Valid @RequestBody PaymentDTO paymentDTO) throws Exception {
        try{
            String result = iPaymentService.createPayment(paymentDTO);
            return ResponseEntity.ok(result);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updatePayment(@PathVariable int id,@Valid @RequestBody PaymentDTO paymentDTO) throws Exception {
        try{
            String rsult = iPaymentService.updatePayment(id,paymentDTO);
            return ResponseEntity.ok(rsult);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deletePayment(@PathVariable int id){
        String rsult = iPaymentService.deletePayment(id);
        return ResponseEntity.ok(rsult);
    }
}
