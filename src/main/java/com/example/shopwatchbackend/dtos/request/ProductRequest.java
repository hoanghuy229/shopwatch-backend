package com.example.shopwatchbackend.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @JsonProperty("name")
    @NotBlank
    private String name;

    @JsonProperty("price")
    @Min(value = 0,message = "must >=0")
    private Double price;

    @JsonProperty("quantity")
    @Min(value = 1,message = "must >=1")
    private int quantity;

    @JsonProperty("description")
    private String description;

    @JsonProperty("image")
    private String image;

    @JsonProperty("category_id")
    private int categoryId;
}
