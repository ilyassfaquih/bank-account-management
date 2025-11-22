package com.example.bankingapp.infrastructure.postgres;

import com.example.bankingapp.domain.model.User;
import com.example.bankingapp.domain.ports.out.UserRepositoryPort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Implémentation Spring Data JPA du port UserRepositoryPort.
 * Les méthodes de la super‑interface JpaRepository sont utilisées
 * par le domaine via le port.
 */
public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepositoryPort {

    @Override
    Optional<User> findByUsername(String username);
}
