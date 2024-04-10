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
public class CustomerResponseList {
    @JsonProperty("customer_list")
    private List<CustomerResponse> customerResponseList;

    @JsonProperty("total_page")
    private int totalPage;
}
