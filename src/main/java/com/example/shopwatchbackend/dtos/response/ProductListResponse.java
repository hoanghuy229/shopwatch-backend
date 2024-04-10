package com.example.shopwatchbackend.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListResponse {

    @JsonProperty("product_responses")
    private List<ProductResponse> productResponses;

    @JsonProperty("total_pages")
    private int totalPages;
}
