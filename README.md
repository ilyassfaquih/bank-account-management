# Bank Account Management System

Java 17 / Spring Boot 3.2.2 banking application following clean architecture principles,
with PostgreSQL for core data, MongoDB for transaction logs, AOP-based transaction logging,
and PDF statement export.

## Main Features

- User registration with associated bank account and role (Customer / Manager)
- User blocking (blocked users cannot perform transactions)
- Local and Forex transactions with validation (balance, currencies)
- All transactions automatically logged to MongoDB using custom annotation + AOP
- PDF bank statement generation from MongoDB transaction history

## Architecture

- `domain`: core model, use case interfaces (ports) and exceptions
- `application`: use case implementations and DTOs
- `infrastructure`: adapters for PostgreSQL (JPA), MongoDB, and AOP
- `web`: REST controllers + exception handler

## Databases

- PostgreSQL: users, bank accounts
- MongoDB: transaction_logs collection

Configure connection strings in `src/main/resources/application.yml`.

## Build & Run

```bash
mvn clean package
java -jar target/bank-account-management-system-0.0.1-SNAPSHOT.jar
```

## Sample REST Endpoints

- `POST /api/users` — register user + account
- `POST /api/users/{id}/block` — block a user
- `POST /api/transactions/local` — local currency transaction
- `POST /api/transactions/forex` — forex transaction
- `GET /api/statements/{accountNumber}/pdf` — download PDF statement
