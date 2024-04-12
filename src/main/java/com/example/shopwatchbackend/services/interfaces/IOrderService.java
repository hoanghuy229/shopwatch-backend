package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.OrderRequest;
import com.example.shopwatchbackend.dtos.response.OrderResponse;

import java.util.List;

public interface IOrderService {
    String createOrder(OrderRequest orderRequest) throws Exception;
    String updateOrder(int id, OrderRequest orderRequest) throws Exception;

    List<OrderResponse> getAllByCustomerId(int id);
}
