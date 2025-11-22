package com.example.bankingapp.infrastructure.aop;

import com.example.bankingapp.application.dto.TransactionRequest;
import com.example.bankingapp.domain.model.TransactionLog;
import com.example.bankingapp.domain.ports.out.TransactionLogPort;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class TransactionLoggingAspect {

    private final TransactionLogPort transactionLogPort;

    @Around("@annotation(logTransaction)")
    public Object logTransaction(ProceedingJoinPoint pjp, LogTransaction logTransaction) throws Throwable {
        Object[] args = pjp.getArgs();
        TransactionRequest request = (TransactionRequest) Arrays.stream(args)
                .filter(a -> a instanceof TransactionRequest)
                .findFirst()
                .orElse(null);

        Object result = pjp.proceed();

        if (request != null) {
            TransactionLog log = TransactionLog.builder()
                    .fromAccount(request.getFromAccount())
                    .toAccount(request.getToAccount())
                    .amount(request.getAmount())
                    .currencyFrom(request.getCurrencyFrom())
                    .currencyTo(request.getCurrencyTo())
                    .type(logTransaction.type())
                    .timestamp(Instant.now())
                    .build();

            transactionLogPort.save(log);
        }

        return result;
    }
}
