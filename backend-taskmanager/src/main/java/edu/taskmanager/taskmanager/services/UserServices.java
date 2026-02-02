package edu.taskmanager.taskmanager.services;

import edu.taskmanager.taskmanager.domain.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.Cookie;


/**
 * Service interface for user-related operations.
 */
@Service
public interface UserServices {

    /**
     * Deletes a user by their email.
     *
     * @param email the email of the user to be deleted
     */
    void deleteUser(String email);

    String getName(User user);

    String getEmail(User user);
}