package edu.taskmanager.taskmanager.controllers;

import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.dto.LoginRequestDto;
import edu.taskmanager.taskmanager.dto.RegisterRequestDto;
import edu.taskmanager.taskmanager.dto.ResponseDto;
import edu.taskmanager.taskmanager.infra.security.TokenService;
import edu.taskmanager.taskmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import edu.taskmanager.taskmanager.infra.security.TokenService;
import edu.taskmanager.taskmanager.infra.security.SecurityConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * AuthController is a REST controller that handles HTTP requests related to authentication.
 * It is annotated with @RestController, meaning it's a controller where every method returns a domain object instead of a view.
 * It's also annotated with @RequestMapping("/auth"), meaning all HTTP requests that match "/auth" will be handled by this controller.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService jwtTokenProvider;

    /**
     * This method is a POST endpoint for user login.
     * It is annotated with @PostMapping, meaning it will respond to HTTP POST requests.
     * When a POST request is made to "/auth/login", this method will be invoked.
     * It checks if the user exists and if the password matches the one in the database.
     * If the login is successful, it generates a JWT token and returns it in the response.
     * @param body - LoginRequestDto object that contains the user's login credentials.
     * @return a ResponseEntity with the user's name and JWT token if login is successful, or a bad request status if not.
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto body) {
        User usuario = userRepository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(body.password(), usuario.getPassword())) {
            String token = this.jwtTokenProvider.generateToken(usuario);
            return ResponseEntity.ok(new ResponseDto(usuario.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * This method is a POST endpoint for user registration.
     * It is annotated with @PostMapping, meaning it will respond to HTTP POST requests.
     * When a POST request is made to "/auth/register", this method will be invoked.
     * It checks if the user already exists in the database.
     * If the user does not exist, it creates a new user, saves it in the database, generates a JWT token and returns it in the response.
     * @param body - RegisterRequestDto object that contains the user's registration details.
     * @return a ResponseEntity with the user's name and JWT token if registration is successful, or a bad request status if not.
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDto body) {

        Optional<User> user = userRepository.findByEmail(body.email());
        if (user.isEmpty()){
            User novoUsuario = new User();
            novoUsuario.setEmail(body.email());
            novoUsuario.setPassword(passwordEncoder.encode(body.password()));
            novoUsuario.setName(body.name());
            this.userRepository.save(novoUsuario);

            String token = this.jwtTokenProvider.generateToken(novoUsuario);
            return ResponseEntity.ok(new ResponseDto(novoUsuario.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }
}