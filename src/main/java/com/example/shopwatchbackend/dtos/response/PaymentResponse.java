package com.example.shopwatchbackend.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    @JsonProperty("payment_id")
    private int paymentId;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("amount")
    private Double amount;

}
