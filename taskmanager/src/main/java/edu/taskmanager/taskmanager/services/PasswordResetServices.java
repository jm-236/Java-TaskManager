package edu.taskmanager.taskmanager.services;

import org.springframework.stereotype.Service;

@Service
public interface PasswordResetServices {

    void sendResetEmail(String email);

    void updatePassword(String token, String email, String password);
}
