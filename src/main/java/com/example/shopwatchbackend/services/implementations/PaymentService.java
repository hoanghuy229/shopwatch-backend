package com.example.shopwatchbackend.services.implementations;

import com.example.shopwatchbackend.dtos.request.PaymentRequest;
import com.example.shopwatchbackend.models.Payment;
import com.example.shopwatchbackend.repositories.PaymentRepository;
import com.example.shopwatchbackend.dtos.response.PaymentResponse;
import com.example.shopwatchbackend.services.interfaces.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<PaymentResponse> getAll() {
        List<Payment> paymentList = paymentRepository.findAll();
        return paymentList.stream().map(payment -> modelMapper.map(payment, PaymentResponse.class)).toList();
    }

    @Override
    @Transactional
    public String createPayment(PaymentRequest paymentRequest) throws Exception {
        Payment exist = paymentRepository.findByPaymentMethod(paymentRequest.getPaymentMethod());
        if(exist != null){
            throw new Exception("existed!!!");
        }
        Payment newPayment = Payment.builder().paymentMethod(paymentRequest.getPaymentMethod()).amount(paymentRequest.getAmount()).build();
        paymentRepository.save(newPayment);
        return "create success";
    }

    @Override
    @Transactional
    public String updatePayment(int id, PaymentRequest paymentRequest) throws Exception {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new Exception("cannot find"));

        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setAmount(paymentRequest.getAmount());
        paymentRepository.save(payment);
        return "update success";
    }

    @Override
    @Transactional
    public String deletePayment(int id) {
        paymentRepository.deleteById(id);
        return "delete success";
    }
}
