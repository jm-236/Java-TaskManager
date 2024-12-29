package edu.taskmanager.taskmanager.repositories;

import edu.taskmanager.taskmanager.domain.passwordResetToken.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
}
