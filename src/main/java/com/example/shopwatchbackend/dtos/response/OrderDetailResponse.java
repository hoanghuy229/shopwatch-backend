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
public class OrderDetailResponse {

    @JsonProperty("id")
    private int orderDetailId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("product_id")
    private int productId;

    @JsonProperty("order_id")
    private int orderId;
}
