package com.example.bankingapp.application.dto;

import com.example.bankingapp.domain.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RegisterUserRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String fullName;

    @NotBlank
    private String password;

    @NotNull
    private Role role;

    @NotBlank
    private String initialCurrency;

    @NotNull
    private BigDecimal initialBalance;
}
