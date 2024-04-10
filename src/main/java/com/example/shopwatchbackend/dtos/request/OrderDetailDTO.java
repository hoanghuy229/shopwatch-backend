package com.example.shopwatchbackend.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {

    @JsonProperty("quantity")
    @Size(min = 1,message = "must >=1")
    private int quantity;

    @JsonProperty("price")
    @Size(min = 0,message = "must >=0")
    private Double price;

    @JsonProperty("product_id")
    @Min(value = 1)
    private int productId;

}
