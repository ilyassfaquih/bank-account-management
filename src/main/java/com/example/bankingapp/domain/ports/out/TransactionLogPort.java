package com.example.bankingapp.domain.ports.out;

import com.example.bankingapp.domain.model.TransactionLog;

import java.util.List;

public interface TransactionLogPort {
    TransactionLog save(TransactionLog log);
    List<TransactionLog> findByAccount(String accountNumber);
}
