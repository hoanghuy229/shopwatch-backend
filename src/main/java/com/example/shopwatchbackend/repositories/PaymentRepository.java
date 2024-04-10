package com.example.shopwatchbackend.repositories;

import com.example.shopwatchbackend.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Payment findByPaymentMethod(String method);
}
