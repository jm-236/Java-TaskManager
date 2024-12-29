package edu.taskmanager.taskmanager.domain.passwordResetToken;

import edu.taskmanager.taskmanager.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * Entity representing a password reset token.
 * This token is used to reset the password of a user.
 */
@Entity
@Table(name = "password_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {

    /**
     * Unique identifier for the password reset token.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The token string used for password reset.
     */
    private String token;

    /**
     * The user associated with this password reset token.
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user; // Relates to the user entity.

    /**
     * The expiry date and time of the password reset token.
     */
    private LocalDateTime expiryDate;
}