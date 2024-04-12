package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.CustomerRequest;
import com.example.shopwatchbackend.dtos.request.LoginRequest;
import com.example.shopwatchbackend.models.Customer;
import com.example.shopwatchbackend.dtos.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ICustomerService {
    Page<CustomerResponse> getAll(String keyword, PageRequest pageRequest);
    String register(CustomerRequest customerRequest) throws Exception;
    String login(LoginRequest loginRequest) throws Exception;

    Customer getUserDetailFromToken(String token) throws Exception;

    String updateCustomerDetail(int id, CustomerRequest customerRequest) throws Exception;

}
