package com.example.bankingapp.domain.ports.in;

import com.example.bankingapp.application.dto.RegisterUserRequest;
import com.example.bankingapp.application.dto.RegisterUserResponse;

public interface RegisterUserUseCase {
    RegisterUserResponse register(RegisterUserRequest request);
}
