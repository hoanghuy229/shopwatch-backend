package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.PaymentDTO;
import com.example.shopwatchbackend.dtos.response.PaymentResponse;

import java.util.List;

public interface IPaymentService {
    List<PaymentResponse> getAll();
    String createPayment(PaymentDTO paymentDTO) throws Exception;
    String updatePayment(int id,PaymentDTO paymentDTO) throws Exception;
    String deletePayment(int id);
}
