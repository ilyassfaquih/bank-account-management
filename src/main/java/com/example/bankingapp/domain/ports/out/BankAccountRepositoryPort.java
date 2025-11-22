package com.example.bankingapp.domain.ports.out;

import com.example.bankingapp.domain.model.BankAccount;

import java.util.Optional;

/**
 * Port de sortie pour la persistance des comptes bancaires.
 * Cette interface ne dépend d'aucune technologie (JPA, Mongo, etc.).
 * L'adaptateur d'infrastructure (par ex. BankAccountJpaRepository)
 * fournit l'implémentation concrète.
 */
public interface BankAccountRepositoryPort {

    /**
     * Sauvegarde ou met à jour un compte bancaire.
     */
    BankAccount save(BankAccount bankAccount);

    /**
     * Recherche un compte par son numéro.
     */
    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
