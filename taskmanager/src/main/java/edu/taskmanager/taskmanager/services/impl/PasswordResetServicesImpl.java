package edu.taskmanager.taskmanager.services.impl;

import edu.taskmanager.taskmanager.domain.passwordResetToken.PasswordResetToken;
import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.repositories.PasswordResetTokenRepository;
import edu.taskmanager.taskmanager.repositories.UserRepository;
import edu.taskmanager.taskmanager.services.PasswordResetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetServicesImpl implements PasswordResetServices {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Value("${app.reset-password.url}")
    private String resetUrl; // URL base para redefinição de senha.

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String remetente;

    @Value("${spring.mail.password}")
    private String senha;

    @Override
    public void sendResetEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        String token = UUID.randomUUID().toString();

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        tokenRepository.save(passwordResetToken);

        String resetLink = resetUrl + "/" + token;

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setFrom(remetente);
        emailMessage.setSubject("Redefinição de senha");
        emailMessage.setText("Clique no link para redefinir sua senha: " + resetLink);
        mailSender.send(emailMessage);
    }

    @Override
    public void updatePassword(String token, String email, String password) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido ou expirado"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expirado");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        tokenRepository.delete(resetToken); // Exclui o token usado.

    }
}
