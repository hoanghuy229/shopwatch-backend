package com.example.shopwatchbackend.repositories;

import com.example.shopwatchbackend.models.Order;
import com.example.shopwatchbackend.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> getByOrderOrderId(int orderId);
}
