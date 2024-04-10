package com.example.shopwatchbackend.services.implementations;

import com.example.shopwatchbackend.component.JwtUtils;
import com.example.shopwatchbackend.dtos.request.CustomerDTO;
import com.example.shopwatchbackend.dtos.request.LoginDTO;
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
    public String register(CustomerDTO customerDTO) throws Exception {
        boolean exist = customerRepository.existsByPhoneNumber(customerDTO.getPhoneNumber());
        if(exist){
            throw new Exception("customer exists");
        }
        Customer customer = new Customer();
        modelMapper.map(customerDTO,customer);
        Role role = roleRepository.findById(2).orElseThrow(() -> new Exception("cannot find role"));
        customer.setRole(role);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setAvatar("C:\\Users\\Admin\\OneDrive\\Máy tính\\wweb\\imgs\\user.jpg");
        customerRepository.save(customer);
        return "register success";
    }

    @Override
    public String login(LoginDTO loginDTO) throws Exception {
        Optional<Customer> customer = customerRepository.findByPhoneNumber(loginDTO.getPhoneNumber());
        if(customer.isEmpty()){
            throw new Exception("user not found");
        }
        Customer exist = customer.get();
        if(!passwordEncoder.matches(loginDTO.getPassword(),exist.getPassword())){
            throw new Exception("password not match!!!");
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loginDTO.getPhoneNumber(),loginDTO.getPassword(),exist.getAuthorities());
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
    public String updateCustomerDetail(int id, CustomerDTO customerDTO) throws Exception {
        Customer existCustomer = customerRepository.findById(id).orElseThrow(() -> new Exception("cannot find user"));
        if(customerDTO.getFirstName() != null){
            existCustomer.setFirstName(customerDTO.getFirstName());
        }
        if(customerDTO.getLastName() != null){
            existCustomer.setLastName(customerDTO.getLastName());
        }
        if(customerDTO.getEmail() != null){
            existCustomer.setEmail(customerDTO.getEmail());
        }
        if(customerDTO.getPassword() != null){
            existCustomer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        }
        customerRepository.save(existCustomer);
        return "update account success";
    }
}
