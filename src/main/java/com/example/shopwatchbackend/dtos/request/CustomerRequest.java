package com.example.shopwatchbackend.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {

    @JsonProperty("first_name")
    @NotBlank(message = "cannot be empty")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "cannot be empty")
    private String lastName;

    @JsonProperty("email")
    @NotBlank(message = "cannot be empty")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "cannot be empty")
    private String password;

    @JsonProperty("phone_number")
    @NotBlank(message = "cannot be empty")
    private String phoneNumber;

}
