package edu.taskmanager.taskmanager.services;

import org.springframework.stereotype.Service;

/**
 * Service interface for password reset-related operations.
 */
@Service
public interface PasswordResetServices {

    /**
     * Sends a password reset email to the specified email address.
     *
     * @param email the email address to send the reset email to
     */
    void sendResetEmail(String email);

    /**
     * Updates the password for the user associated with the given token and email.
     *
     * @param token the password reset token
     * @param email the email address of the user
     * @param password the new password to set
     */
    void updatePassword(String token, String email, String password);
}