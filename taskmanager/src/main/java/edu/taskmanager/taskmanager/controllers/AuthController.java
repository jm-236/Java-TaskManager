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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService jwtTokenProvider;

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
