package com.example.bankingapp.domain.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "transaction_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionLog {

    @Id
    private String id;

    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String currencyFrom;
    private String currencyTo;
    private TransactionType type;
    private Instant timestamp;
}
