package com.example.shopwatchbackend.services.implementations;

import com.example.shopwatchbackend.dtos.request.OrderDetailDTO;
import com.example.shopwatchbackend.models.Order;
import com.example.shopwatchbackend.models.OrderDetail;
import com.example.shopwatchbackend.models.Product;
import com.example.shopwatchbackend.repositories.OrderDetailRepository;
import com.example.shopwatchbackend.repositories.OrderRepository;
import com.example.shopwatchbackend.repositories.ProductRepository;
import com.example.shopwatchbackend.dtos.response.OrderDetailResponse;
import com.example.shopwatchbackend.services.interfaces.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderDetailResponse> getAllByOrderId(int id) throws Exception {
        Order existOrder = orderRepository.findById(id).orElseThrow(() -> new Exception("cannot find order"));
        List<OrderDetail> orderDetailList = orderDetailRepository.getByOrderOrderId(existOrder.getOrderId());

        return orderDetailList.stream().map(orderDetail -> modelMapper.map(orderDetail,OrderDetailResponse.class)).toList();
    }

    @Override
    @Transactional
    public String createOrderDetail(int id,OrderDetailDTO orderDetailDTO) throws Exception {
        Product product = productRepository.findById(orderDetailDTO.getProductId()).orElseThrow(() -> new Exception("cannot find product"));
        Order order =  orderRepository.findById(id).orElseThrow(() -> new Exception("cannot find order"));
        OrderDetail orderDetail = new OrderDetail();
        modelMapper.map(orderDetailDTO,orderDetail);
        orderDetail.setProduct(product);
        orderDetail.setOrder(order);
        orderDetailRepository.save(orderDetail);
        return "success";
    }

    @Override
    @Transactional
    public String updateOrderDetail(int id, OrderDetailDTO orderDetailDTO) {
        return null;
    }
}
