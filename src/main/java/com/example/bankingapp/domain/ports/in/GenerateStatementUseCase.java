package com.example.bankingapp.domain.ports.in;

public interface GenerateStatementUseCase {
    byte[] generateStatement(String accountNumber);
}
