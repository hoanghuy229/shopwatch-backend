package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.OrderDetailRequest;
import com.example.shopwatchbackend.dtos.response.OrderDetailResponse;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetailResponse> getAllByOrderId(int id) throws Exception;
    String createOrderDetail(int id, OrderDetailRequest orderDetailRequest) throws Exception;
    String updateOrderDetail(int id, OrderDetailRequest orderDetailRequest);
}
