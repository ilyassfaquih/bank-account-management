package com.example.bankingapp.domain.ports.out;

import com.example.bankingapp.domain.model.User;

import java.util.Optional;

/**
 * Port de sortie pour la persistance des utilisateurs.
 * Interface indépendante de la technologie de stockage.
 */
public interface UserRepositoryPort {

    /**
     * Sauvegarde ou met à jour un utilisateur.
     */
    User save(User user);

    /**
     * Recherche un utilisateur par identifiant technique.
     */
    Optional<User> findById(Long id);

    /**
     * Recherche un utilisateur par nom d'utilisateur.
     */
    Optional<User> findByUsername(String username);
}
