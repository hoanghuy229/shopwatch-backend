package com.example.shopwatchbackend.controllers;

import com.example.shopwatchbackend.dtos.request.CustomerRequest;
import com.example.shopwatchbackend.dtos.request.LoginRequest;
import com.example.shopwatchbackend.models.Customer;
import com.example.shopwatchbackend.dtos.response.CustomerResponse;
import com.example.shopwatchbackend.dtos.response.CustomerResponseList;
import com.example.shopwatchbackend.services.interfaces.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("${api.prefix}/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService iCustomerService;
    private final ModelMapper modelMapper;

    @GetMapping("/admin/get-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "12",name = "limit") int limit,
            @RequestParam(defaultValue = "",name = "keyword") String keyword){

        PageRequest pageRequest = PageRequest.of(page,limit, Sort.by("customerId").descending());
        Page<CustomerResponse> customerResponses = iCustomerService.getAll(keyword,pageRequest);
        int totalPage = customerResponses.getTotalPages();
        List<CustomerResponse> customerResponseList = customerResponses.getContent();
        return ResponseEntity.ok(CustomerResponseList.builder().customerResponseList(customerResponseList).totalPage(totalPage).build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
       try{
           String token = iCustomerService.login(loginRequest);
           return ResponseEntity.ok(token);
       }
       catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    @GetMapping("/details")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> getUserDetail(@RequestHeader("Authorization") String token){
        try{
            String extractToken = token.substring(7);
            Customer user = iCustomerService.getUserDetailFromToken(extractToken);
            return ResponseEntity.ok().body(modelMapper.map(user,CustomerResponse.class));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createAccount(@Valid @RequestBody CustomerRequest customerRequest) throws Exception {
        try{
            String result = iCustomerService.register(customerRequest);
            return ResponseEntity.ok(result);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/details/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateCustomer(@PathVariable int id,
                                            @Valid @RequestBody CustomerRequest customerRequest,
                                            @RequestHeader("Authorization") String token){
        try{
            String extractToken = token.substring(7);
            Customer customer = iCustomerService.getUserDetailFromToken(extractToken);
            if(customer.getCustomerId() != id){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            String result = iCustomerService.updateCustomerDetail(id, customerRequest);
            return ResponseEntity.ok().body(result);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id){

        return ResponseEntity.ok("delete "+ id);
    }
}
