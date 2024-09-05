package edu.taskmanager.taskmanager.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.repositories.UserRepository;

/*
* Classe SecurityFilter que extende OncePerRequestFilter para interceptar as requisições
* e validar o token do usuário para autenticação e autorização.
* A classe é um componente do Spring e é injetada no contexto da aplicação.
*/

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token); // validação do token recuperado(retorna o email)

        if(login != null){
            // Buscar o usuário no banco de dados
            User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User Not Found"));

            // Guardando as permissões do usuário
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

            // Criando a autenticação do usuário
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        // método que retorna o token do cabeçalho da requisição
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
