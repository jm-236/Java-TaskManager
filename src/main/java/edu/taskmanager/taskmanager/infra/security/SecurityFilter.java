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

/**
 * SecurityFilter class extends OncePerRequestFilter to intercept requests
 * and validate the user's token for authentication and authorization.
 * The class is a Spring component and is injected into the application context.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    /**
     * Filters incoming requests to validate the user's token.
     * @param request - The HttpServletRequest object.
     * @param response - The HttpServletResponse object.
     * @param filterChain - The FilterChain object.
     * @throws ServletException if an error occurs during the filtering process.
     * @throws IOException if an I/O error occurs during the filtering process.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token); // validates the recovered token (returns the email)

        if(login != null){
            // Fetch the user from the database
            User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User Not Found"));

            // Storing the user's permissions
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

            // Creating the user's authentication
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Retrieves the token from the request header.
     * @param request - The HttpServletRequest object.
     * @return the token as a String.
     */
    private String recoverToken(HttpServletRequest request){
        // method that returns the token from the request header
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}