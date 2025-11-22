package com.example.bankingapp.domain.ports.in;

import com.example.bankingapp.application.dto.TransactionRequest;

public interface PerformTransactionUseCase {
    void performLocalTransaction(TransactionRequest request);
    void performForexTransaction(TransactionRequest request);
}
