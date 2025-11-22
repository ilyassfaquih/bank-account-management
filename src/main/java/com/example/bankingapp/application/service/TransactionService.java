package com.example.bankingapp.application.service;

import com.example.bankingapp.application.dto.TransactionRequest;
import com.example.bankingapp.domain.exception.BusinessException;
import com.example.bankingapp.domain.exception.NotFoundException;
import com.example.bankingapp.domain.model.BankAccount;
import com.example.bankingapp.domain.model.TransactionType;
import com.example.bankingapp.domain.model.User;
import com.example.bankingapp.domain.ports.in.PerformTransactionUseCase;
import com.example.bankingapp.domain.ports.out.BankAccountRepositoryPort;
import com.example.bankingapp.domain.ports.out.UserRepositoryPort;
import com.example.bankingapp.infrastructure.aop.LogTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService implements PerformTransactionUseCase {

    private final BankAccountRepositoryPort bankAccountRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    @Transactional
    @LogTransaction(type = TransactionType.LOCAL)
    public void performLocalTransaction(TransactionRequest request) {
        validateCurrenciesForLocal(request);
        performTransfer(request, false);
    }

    @Override
    @Transactional
    @LogTransaction(type = TransactionType.FOREX)
    public void performForexTransaction(TransactionRequest request) {
        if (request.getForexRate() == null || request.getForexRate().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Invalid forex rate");
        }
        performTransfer(request, true);
    }

    private void validateCurrenciesForLocal(TransactionRequest request) {
        if (!request.getCurrencyFrom().equals(request.getCurrencyTo())) {
            throw new BusinessException("Local transaction requires same currency");
        }
    }

    private void performTransfer(TransactionRequest request, boolean forex) {
        BankAccount from = bankAccountRepositoryPort.findByAccountNumber(request.getFromAccount())
                .orElseThrow(() -> new NotFoundException("From account not found"));
        BankAccount to = bankAccountRepositoryPort.findByAccountNumber(request.getToAccount())
                .orElseThrow(() -> new NotFoundException("To account not found"));

        User fromUser = from.getUser();
        if (fromUser.isBlocked()) {
            throw new BusinessException("User is blocked and cannot perform transactions");
        }

        BigDecimal debitAmount = request.getAmount();
        if (from.getBalance().compareTo(debitAmount) < 0) {
            throw new BusinessException("Insufficient balance");
        }

        from.debit(debitAmount);

        BigDecimal creditAmount = forex
                ? debitAmount.multiply(request.getForexRate())
                : debitAmount;

        to.credit(creditAmount);

        bankAccountRepositoryPort.save(from);
        bankAccountRepositoryPort.save(to);
    }
}
