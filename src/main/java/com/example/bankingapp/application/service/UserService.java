package com.example.bankingapp.application.service;

import com.example.bankingapp.application.dto.RegisterUserRequest;
import com.example.bankingapp.application.dto.RegisterUserResponse;
import com.example.bankingapp.domain.exception.BusinessException;
import com.example.bankingapp.domain.exception.NotFoundException;
import com.example.bankingapp.domain.model.BankAccount;
import com.example.bankingapp.domain.model.User;
import com.example.bankingapp.domain.ports.in.BlockUserUseCase;
import com.example.bankingapp.domain.ports.in.RegisterUserUseCase;
import com.example.bankingapp.domain.ports.out.BankAccountRepositoryPort;
import com.example.bankingapp.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements RegisterUserUseCase, BlockUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final BankAccountRepositoryPort bankAccountRepositoryPort;

    @Override
    @Transactional
    public RegisterUserResponse register(RegisterUserRequest request) {
        // Vérifier unicité du username
        userRepositoryPort.findByUsername(request.getUsername()).ifPresent(u -> {
            throw new BusinessException("Username already exists");
        });

        // Créer le user (mot de passe non hashé ici, pour simplifier)
        User user = User.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .password(request.getPassword())
                .role(request.getRole())
                .blocked(false)
                .build();

        // Créer le compte bancaire
        BankAccount account = BankAccount.builder()
                .accountNumber(UUID.randomUUID().toString())
                .currency(request.getInitialCurrency())
                .balance(request.getInitialBalance() != null
                        ? request.getInitialBalance()
                        : BigDecimal.ZERO)
                .user(user)
                .build();

        user.setBankAccount(account);

        // Sauvegarder
        User savedUser = userRepositoryPort.save(user);
        bankAccountRepositoryPort.save(account);

        // Réponse
        return RegisterUserResponse.builder()
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .accountNumber(account.getAccountNumber())
                .build();
    }

    @Override
    @Transactional
    public void blockUser(Long userId) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.block();
        userRepositoryPort.save(user);
    }
}
