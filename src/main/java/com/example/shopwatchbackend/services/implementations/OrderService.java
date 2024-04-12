package com.example.shopwatchbackend.services.implementations;

import com.example.shopwatchbackend.dtos.request.OrderRequest;
import com.example.shopwatchbackend.dtos.request.OrderDetailRequest;
import com.example.shopwatchbackend.models.*;
import com.example.shopwatchbackend.repositories.*;
import com.example.shopwatchbackend.dtos.response.OrderDetailResponse;
import com.example.shopwatchbackend.dtos.response.OrderResponse;
import com.example.shopwatchbackend.services.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public String createOrder(OrderRequest orderRequest) throws Exception {
        Customer exist = customerRepository.findById(orderRequest.getCustomerId()).orElseThrow(() -> new Exception("cannot find user"));
        Payment payment = paymentRepository.findById(orderRequest.getPaymentId()).orElseThrow(() -> new Exception("cannot find payment"));
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setCustomer(exist);
        order.setPayment(payment);
        order.setTotalPrice(orderRequest.getTotalPrice());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        for(OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetailRequestList()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order); // Đặt orderId cho OrderDetail
            orderDetail.setQuantity(orderDetailRequest.getQuantity());
            orderDetail.setPrice(orderDetailRequest.getPrice()); // Thêm giá vào OrderDetail
            Product product = productRepository.findById(orderDetailRequest.getProductId()).orElseThrow(() -> new Exception("cannot find product"));
            orderDetail.setProduct(product);
            product.setQuantity(product.getQuantity() - orderDetailRequest.getQuantity());
            productRepository.save(product);
            orderDetailList.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetailList);
        orderRepository.save(order);
        return "create order success";
    }


    @Override
    @Transactional
    public String updateOrder(int id, OrderRequest orderRequest) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow(() -> new Exception("cannot find order"));
        modelMapper.map(orderRequest,order);
        orderRepository.save(order);
        return "update success";
    }

    @Override
    public List<OrderResponse> getAllByCustomerId(int id) {
       List<Order> orderList = orderRepository.findByCustomerCustomerId(id);
       List<OrderResponse> orderResponseList = new ArrayList<>();
       for(Order order : orderList){
           OrderResponse orderResponse = new OrderResponse();
           modelMapper.map(order,orderResponse);
           List<OrderDetail> orderDetails = order.getOrderDetailList();
           List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
           for(OrderDetail orderDetail : orderDetails){
               OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
               orderDetailResponse.setOrderDetailId(orderDetail.getOrderDetailId());
               orderDetailResponse.setQuantity(orderDetail.getQuantity());
               orderDetailResponse.setPrice(orderDetail.getPrice());
               orderDetailResponse.setProductId(orderDetail.getProduct().getProductId());
               orderDetailResponse.setOrderId(orderDetail.getOrder().getOrderId());
               orderDetailResponses.add(orderDetailResponse);
           }
           orderResponse.setOrderDetailResponseList(orderDetailResponses);
           orderResponseList.add(orderResponse);
       }
       return orderResponseList;
    }
}
