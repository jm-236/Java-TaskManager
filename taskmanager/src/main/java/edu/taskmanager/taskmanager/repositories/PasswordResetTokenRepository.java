package edu.taskmanager.taskmanager.repositories;

import edu.taskmanager.taskmanager.domain.passwordResetToken.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Repository interface for managing PasswordResetToken entities.
 * Extends JpaRepository to provide CRUD operations.
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    /**
     * Finds a PasswordResetToken by its token string.
     *
     * @param token the token string to search for
     * @return an Optional containing the found PasswordResetToken, or empty if not found
     */
    Optional<PasswordResetToken> findByToken(String token);
}
