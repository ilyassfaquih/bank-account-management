package com.example.bankingapp.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {

    @NotBlank
    private String fromAccount;

    @NotBlank
    private String toAccount;

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String currencyFrom;

    @NotBlank
    private String currencyTo;

    // For simplicity, forex rate can be 1 for local transactions
    @NotNull
    private java.math.BigDecimal forexRate;
}
