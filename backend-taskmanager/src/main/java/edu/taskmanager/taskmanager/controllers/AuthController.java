package edu.taskmanager.taskmanager.controllers;

import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.dto.*;
import edu.taskmanager.taskmanager.infra.security.TokenService;
import edu.taskmanager.taskmanager.services.impl.AuthServicesImpl;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final TokenService jwtTokenProvider;
    private final AuthServicesImpl authServices;

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
        User usuario = authServices.checkIfUserExists(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (authServices.checkPassword(usuario, body.password())) {

            Cookie cookie = authServices.generateJWT(usuario);

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Set-Cookie", String.format("%s=%s; Path=%s; Max-Age=%d; HttpOnly",
                            cookie.getName(), cookie.getValue(), cookie.getPath(), cookie.getMaxAge()))
                    .body(new ResponseDto(usuario.getName()));
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new BadRegisterDto("Usuário ou senha incorretos"));
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

        Optional<User> user = authServices.checkIfUserExists(body.email());
        if (user.isEmpty()){

            User newUser = authServices.saveNewUser(body.email(), body.name(), body.password());

            Cookie cookie = authServices.generateJWT(newUser);

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Set-Cookie", String.format("%s=%s; Path=%s; Max-Age=%d; HttpOnly",
                    cookie.getName(), cookie.getValue(), cookie.getPath(), cookie.getMaxAge()))
                    .body(new ResponseDto(newUser.getName()));
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