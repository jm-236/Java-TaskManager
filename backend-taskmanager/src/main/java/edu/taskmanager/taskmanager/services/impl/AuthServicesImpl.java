package edu.taskmanager.taskmanager.services.impl;

import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.infra.security.TokenService;
import edu.taskmanager.taskmanager.repositories.UserRepository;
import edu.taskmanager.taskmanager.services.AuthServices;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServicesImpl implements AuthServices {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService jwtTokenProvider;

    @Override
    public Optional<User> checkIfUserExists(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean checkPassword(User usuario, String senha){
        return passwordEncoder.matches(senha, usuario.getPassword());
    }

    @Override
    public Cookie generateJWT(User user) {

        String token = this.jwtTokenProvider.generateToken(user);
        Cookie cookie = new Cookie("JWTCookie", token);
        cookie.setPath("/");
        cookie.setMaxAge(7200);
        cookie.setHttpOnly(true);

        return cookie;
    }

    @Override
    public User saveNewUser(String email, String name, String password){
        User novoUsuario = new User();
        novoUsuario.setEmail(email);
        novoUsuario.setPassword(passwordEncoder.encode(password));
        novoUsuario.setName(name);
        this.userRepository.save(novoUsuario);

        return novoUsuario;
    }
}
