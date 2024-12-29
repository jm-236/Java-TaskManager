package edu.taskmanager.taskmanager.domain.passwordResetToken;

import edu.taskmanager.taskmanager.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user; // Relacione com a entidade de usuários.

    private LocalDateTime expiryDate;

}
