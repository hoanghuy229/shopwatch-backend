package com.example.shopwatchbackend.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    @JsonProperty("id")
    private int orderId;

    @JsonProperty("order_date")
    private LocalDateTime orderDate;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("customer_id")
    private int customerId;

    @JsonProperty("payment_id")
    private int paymentId;

    @JsonProperty("order_details")
    private List<OrderDetailResponse> orderDetailResponseList;
}
