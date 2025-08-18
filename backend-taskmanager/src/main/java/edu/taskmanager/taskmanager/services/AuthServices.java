package edu.taskmanager.taskmanager.services;

import edu.taskmanager.taskmanager.domain.user.User;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AuthServices {

    Optional<User> checkIfUserExists(String email);

    boolean checkPassword(User usuario, String senha);

    Cookie generateJWT(User user);

    User saveNewUser(String name, String email, String password);
}
