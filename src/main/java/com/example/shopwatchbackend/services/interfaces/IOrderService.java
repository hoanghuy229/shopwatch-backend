package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.OrderDTO;
import com.example.shopwatchbackend.dtos.response.OrderResponse;

import java.util.List;

public interface IOrderService {
    String createOrder(OrderDTO orderDTO) throws Exception;
    String updateOrder(int id, OrderDTO orderDTO) throws Exception;

    List<OrderResponse> getAllByCustomerId(int id);
}
