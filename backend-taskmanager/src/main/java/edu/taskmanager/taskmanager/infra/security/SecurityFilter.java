package edu.taskmanager.taskmanager.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.repositories.UserRepository;

/**
 * SecurityFilter class extends OncePerRequestFilter to intercept requests
 * and validate the user's token for authentication and authorization.
 * The class is a Spring component and is injected into the application context.
 */
@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UserRepository userRepository;

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

        // Lista de paths que devem ser ignorados pelo filtro de segurança
        List<String> publicPaths = Arrays.asList("/auth/login", "/auth/register", "/h2-console");

        String path = request.getRequestURI();

        // Se o path da requisição está na lista de paths públicos,
        // simplesmente continue a cadeia de filtros sem fazer nenhuma validação de token.
        if (publicPaths.stream().anyMatch(p -> path.startsWith(p))) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = this.recoverTokenFromCookie(request);
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
    private String recoverTokenFromAuthHeader(HttpServletRequest request){
        // method that returns the token from the request header
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

    private String recoverTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWTCookie".equals(cookie.getName())) {
                    return "Bearer " + cookie.getValue();
                }
            }
        }
        return null;
    }
}