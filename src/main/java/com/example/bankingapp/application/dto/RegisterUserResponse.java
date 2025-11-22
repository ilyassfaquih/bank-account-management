package com.example.bankingapp.application.dto;

import com.example.bankingapp.domain.model.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserResponse {

    private Long userId;
    private String username;
    private Role role;
    private String accountNumber;
}
