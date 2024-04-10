package com.example.shopwatchbackend.repositories;

import com.example.shopwatchbackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByCustomerCustomerId(int customerId);
}
