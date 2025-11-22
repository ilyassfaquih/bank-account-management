package com.example.bankingapp.infrastructure.postgres;

import com.example.bankingapp.domain.model.BankAccount;
import com.example.bankingapp.domain.ports.out.BankAccountRepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountJpaRepository extends JpaRepository<BankAccount, Long>, BankAccountRepositoryPort {
    @Override
    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
