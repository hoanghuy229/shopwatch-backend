package com.example.shopwatchbackend.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("amount")
    private Double amount;

}
