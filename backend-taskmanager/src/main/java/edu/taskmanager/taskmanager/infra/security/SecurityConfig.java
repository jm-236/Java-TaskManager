package edu.taskmanager.taskmanager.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig class that handles the security configuration of the web application.
 * This class is marked as a configuration class and is initialized during application startup.
 */
@Configuration // Indica que essa é uma classe de configuração e deve ser inicializada durante a inicialização
              // do aplicativo
@EnableWebSecurity // Classe que cuida da configuração de segurança do aplicativo web
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;


    final SecurityFilter securityFilter; // filtro nas requisicoes que devem ser autenticadas

    /**
     * This method configures the security filter chain with custom settings.
     * @param http The HttpSecurity object to be configured
     * @return The configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // liberação dos endpoints de login e registro para cadastro e login dos usuários
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/status").permitAll()
                        .requestMatchers("/h2-console/**").permitAll() // acesso ao console do banco de dados
                        // para qualquer outra camada ele deve estar autenticado
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions().disable())
                // adição do filtro de segurança que valida o token do usuário antes de permitir o acesso
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * This method provides a PasswordEncoder bean for encoding passwords.
     * @return A PasswordEncoder object
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method provides an AuthenticationManager bean for authenticating users.
     * @param authenticationConfiguration The AuthenticationConfiguration object to be used
     * @return An AuthenticationManager object
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
