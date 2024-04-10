package com.example.shopwatchbackend.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {

    @JsonProperty("role_id")
    private int roleId;

    @JsonProperty("role_name")
    private String roleName;

}
