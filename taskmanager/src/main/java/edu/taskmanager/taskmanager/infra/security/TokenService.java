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

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    // Método para gerar um token
    public String generateToken(User user){

        try {
            // Algoritmo que irá criptografar a informação
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Criação do token
            String token = JWT.create()
                    .withIssuer("taskmanager") // microserviço que está gerando o token
                    .withSubject(user.getEmail()) // usuário que está gerando o token
                    .withExpiresAt(this.generateExpirationDate()) // tempo de expiração do token;
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while authenticating user.");
        }
    }

    // Método para gerar a data de expiração do token
    private Instant generateExpirationDate() {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusSeconds(7200);
        return zdt.toInstant();
    }

    public String getUserEmailFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            return validateToken(token.substring(7));
        }
        return validateToken(token);
    }

    // Método para validar o token
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Conjunto de métodos que decodificam o token e retornam o objeto guardado nele(email do usuário)
            return JWT.require(algorithm)
                    .withIssuer("taskmanager")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null; // Se o token for inválido, o retorno é nulo e a autenticação falha
        }
    }
}
