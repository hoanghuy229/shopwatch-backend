package com.example.shopwatchbackend.repositories;

import com.example.shopwatchbackend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
