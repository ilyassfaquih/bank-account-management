package com.example.bankingapp.infrastructure.aop;

import com.example.bankingapp.domain.model.TransactionType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogTransaction {
    TransactionType type();
}
