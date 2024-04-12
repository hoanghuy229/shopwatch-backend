package com.example.shopwatchbackend.services.implementations;

import com.example.shopwatchbackend.component.JwtUtils;
import com.example.shopwatchbackend.dtos.request.CustomerRequest;
import com.example.shopwatchbackend.dtos.request.LoginRequest;
import com.example.shopwatchbackend.models.Customer;
import com.example.shopwatchbackend.models.Role;
import com.example.shopwatchbackend.repositories.CustomerRepository;
import com.example.shopwatchbackend.repositories.RoleRepository;
import com.example.shopwatchbackend.dtos.response.CustomerResponse;
import com.example.shopwatchbackend.services.interfaces.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public Page<CustomerResponse> getAll(String keyword, PageRequest pageRequest) {
        return null;
    }

    @Override
    @Transactional
    public String register(CustomerRequest customerRequest) throws Exception {
        boolean exist = customerRepository.existsByPhoneNumber(customerRequest.getPhoneNumber());
        if(exist){
            throw new Exception("customer exists");
        }
        Customer customer = new Customer();
        modelMapper.map(customerRequest,customer);
        Role role = roleRepository.findById(2).orElseThrow(() -> new Exception("cannot find role"));
        customer.setRole(role);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
        return "register success";
    }

    @Override
    public String login(LoginRequest loginRequest) throws Exception {
        Optional<Customer> customer = customerRepository.findByPhoneNumber(loginRequest.getPhoneNumber());
        if(customer.isEmpty()){
            throw new Exception("user not found");
        }
        Customer exist = customer.get();
        if(!passwordEncoder.matches(loginRequest.getPassword(),exist.getPassword())){
            throw new Exception("password not match!!!");
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loginRequest.getPhoneNumber(), loginRequest.getPassword(),exist.getAuthorities());
        authenticationManager.authenticate(auth);
        return jwtUtils.generateToken(exist);
    }

    @Override
    public Customer getUserDetailFromToken(String token) throws Exception {
        if(jwtUtils.tokenExpired(token)){
            throw new Exception("token is expired");
        }
        String phoneNumber = jwtUtils.extractSubject(token);
        Optional<Customer> customer = customerRepository.findByPhoneNumber(phoneNumber);
        if(customer.isEmpty()){
            throw new Exception("user not found");
        }
        else{
            return customer.get();
        }
    }

    @Override
    @Transactional
    public String updateCustomerDetail(int id, CustomerRequest customerRequest) throws Exception {
        Customer existCustomer = customerRepository.findById(id).orElseThrow(() -> new Exception("cannot find user"));
        if(customerRequest.getFirstName() != null){
            existCustomer.setFirstName(customerRequest.getFirstName());
        }
        if(customerRequest.getLastName() != null){
            existCustomer.setLastName(customerRequest.getLastName());
        }
        if(customerRequest.getEmail() != null){
            existCustomer.setEmail(customerRequest.getEmail());
        }
        if(customerRequest.getPassword() != null){
            existCustomer.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        }
        customerRepository.save(existCustomer);
        return "update account success";
    }

}
