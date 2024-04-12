package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.PaymentRequest;
import com.example.shopwatchbackend.dtos.response.PaymentResponse;

import java.util.List;

public interface IPaymentService {
    List<PaymentResponse> getAll();
    String createPayment(PaymentRequest paymentRequest) throws Exception;
    String updatePayment(int id, PaymentRequest paymentRequest) throws Exception;
    String deletePayment(int id);
}
