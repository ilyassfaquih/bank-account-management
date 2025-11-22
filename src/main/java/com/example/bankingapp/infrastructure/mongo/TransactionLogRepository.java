package com.example.bankingapp.infrastructure.mongo;

import com.example.bankingapp.domain.model.TransactionLog;
import com.example.bankingapp.domain.ports.out.TransactionLogPort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionLogRepository extends MongoRepository<TransactionLog, String>, TransactionLogPort {

    List<TransactionLog> findByFromAccountOrToAccount(String fromAccount, String toAccount);

    @Override
    default List<TransactionLog> findByAccount(String accountNumber) {
        return findByFromAccountOrToAccount(accountNumber, accountNumber);
    }
}
