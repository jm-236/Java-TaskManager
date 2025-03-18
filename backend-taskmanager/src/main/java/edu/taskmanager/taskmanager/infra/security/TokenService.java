package edu.taskmanager.taskmanager.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import edu.taskmanager.taskmanager.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * TokenService is a service class that provides methods for generating and validating JWT tokens.
 */
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Generates a JWT token for the given user.
     * @param user - The user for whom the token is to be generated.
     * @return the generated JWT token as a String.
     * @throws RuntimeException if an error occurs while creating the token.
     */
    public String generateToken(User user){

        try {
            // Algorithm used to encrypt the information
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Token creation
            String token = JWT.create()
                    .withIssuer("taskmanager") // microservice generating the token
                    .withSubject(user.getEmail()) // user generating the token
                    .withExpiresAt(this.generateExpirationDate()) // token expiration time
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while authenticating user.");
        }
    }

    /**
     * Generates the expiration date for the token.
     * @return the expiration date as an Instant.
     */
    private Instant generateExpirationDate() {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusSeconds(7200);
        return zdt.toInstant();
    }

    /**
     * Extracts the user's email from the given token.
     * @param token - The JWT token.
     * @return the user's email as a String.
     */
    public String getUserEmailFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            return validateToken(token.substring(7));
        }
        return validateToken(token);
    }

    /**
     * Validates the given JWT token.
     * @param token - The JWT token to be validated.
     * @return the subject (user's email) if the token is valid, or null if invalid.
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Methods to decode the token and return the stored object (user's email)
            return JWT.require(algorithm)
                    .withIssuer("taskmanager")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null; // If the token is invalid, return null and authentication fails
        }
    }
}