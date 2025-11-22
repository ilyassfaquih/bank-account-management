package com.example.bankingapp.web;

import com.example.bankingapp.application.dto.TransactionRequest;
import com.example.bankingapp.domain.ports.in.PerformTransactionUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final PerformTransactionUseCase performTransactionUseCase;

    @PostMapping("/local")
    public ResponseEntity<Void> local(@Valid @RequestBody TransactionRequest request) {
        performTransactionUseCase.performLocalTransaction(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forex")
    public ResponseEntity<Void> forex(@Valid @RequestBody TransactionRequest request) {
        performTransactionUseCase.performForexTransaction(request);
        return ResponseEntity.ok().build();
    }
}
