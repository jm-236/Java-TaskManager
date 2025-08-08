package edu.taskmanager.taskmanager.config;

import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception{
        seedAdminUser();
    }

    private void seedAdminUser() {

        String email = "joao@gmail.com";
        String nome = "joao";
        String senha = "123";

        if (userRepository.findByEmail(email).isEmpty()) {

            User user = new User();
            user.setEmail(email);
            user.setName(nome);
            user.setPassword(passwordEncoder.encode(senha));

            this.userRepository.save(user);
            log.info("Usuário \'João\' salvo no banco de dados com sucesso!\n");
        }
    }
}
