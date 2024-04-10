package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.CustomerDTO;
import com.example.shopwatchbackend.dtos.request.LoginDTO;
import com.example.shopwatchbackend.models.Customer;
import com.example.shopwatchbackend.dtos.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ICustomerService {
    Page<CustomerResponse> getAll(String keyword, PageRequest pageRequest);
    String register(CustomerDTO customerDTO) throws Exception;
    String login(LoginDTO loginDTO) throws Exception;

    Customer getUserDetailFromToken(String token) throws Exception;

    String updateCustomerDetail(int id,CustomerDTO customerDTO) throws Exception;
}
