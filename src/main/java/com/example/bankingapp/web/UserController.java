package com.example.bankingapp.web;

import com.example.bankingapp.application.dto.RegisterUserRequest;
import com.example.bankingapp.application.dto.RegisterUserResponse;
import com.example.bankingapp.domain.ports.in.BlockUserUseCase;
import com.example.bankingapp.domain.ports.in.RegisterUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final BlockUserUseCase blockUserUseCase;

    @PostMapping
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        return ResponseEntity.ok(registerUserUseCase.register(request));
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<Void> blockUser(@PathVariable Long id) {
        blockUserUseCase.blockUser(id);
        return ResponseEntity.noContent().build();
    }
}
