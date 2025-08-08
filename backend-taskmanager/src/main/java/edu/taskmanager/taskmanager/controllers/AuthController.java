package edu.taskmanager.taskmanager.controllers;

import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.dto.*;
import edu.taskmanager.taskmanager.infra.security.TokenService;
import edu.taskmanager.taskmanager.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import edu.taskmanager.taskmanager.infra.security.TokenService;
import edu.taskmanager.taskmanager.infra.security.SecurityConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
            Cookie cookie = new Cookie("JWTCookie", token);
            cookie.setPath("/");
            cookie.setMaxAge(7200);
            cookie.setHttpOnly(true);

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Set-Cookie", String.format("%s=%s; Path=%s; Max-Age=%d; HttpOnly",
                            cookie.getName(), cookie.getValue(), cookie.getPath(), cookie.getMaxAge()))
                    .body(new ResponseDto(usuario.getName()));
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
            Cookie cookie = new Cookie("JWTCookie", token);
            cookie.setPath("/"); // Define o caminho do cookie
            cookie.setMaxAge(7200); // Define a idade do cookie em segundos (1 dia)
            cookie.setHttpOnly(true); // Torna o cookie inacessível por scripts JavaScript


            return ResponseEntity.status(HttpStatus.OK)
                    .header("Set-Cookie", String.format("%s=%s; Path=%s; Max-Age=%d; HttpOnly",
                    cookie.getName(), cookie.getValue(), cookie.getPath(), cookie.getMaxAge()))
                    .body(new ResponseDto(novoUsuario.getName()));
        }

        return ResponseEntity.badRequest().body(new BadRegisterDto(
                "Usuário com email " + body.email() + " já cadastrado no sistema."
        ));
    }

    @GetMapping("/status")
    public ResponseEntity getUserStatus(@CookieValue("JWTCookie") String token) {

        String email = jwtTokenProvider.getUserEmailFromToken(token);

        if (email.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(new IsLoggedDto(email));
        }

    }
}