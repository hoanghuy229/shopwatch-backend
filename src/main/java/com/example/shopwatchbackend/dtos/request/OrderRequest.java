package com.example.shopwatchbackend.dtos.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    @JsonProperty("total_price")
    @Min(value = 0,message = "must >=0")
    private Double totalPrice;

    @JsonProperty("customer_id")
    @NotNull(message = "cannot be null")
    private int customerId;

    @JsonProperty("payment_id")
    @NotNull(message = "cannot be null")
    private int paymentId;

    @JsonProperty("order_details")
    private List<OrderDetailRequest> orderDetailRequestList;
}
