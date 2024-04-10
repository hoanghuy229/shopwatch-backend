package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.OrderDetailDTO;
import com.example.shopwatchbackend.dtos.response.OrderDetailResponse;

import java.util.List;

public interface IOrderDetailService {
    List<OrderDetailResponse> getAllByOrderId(int id) throws Exception;
    String createOrderDetail(int id,OrderDetailDTO orderDetailDTO) throws Exception;
    String updateOrderDetail(int id,OrderDetailDTO orderDetailDTO);
}
