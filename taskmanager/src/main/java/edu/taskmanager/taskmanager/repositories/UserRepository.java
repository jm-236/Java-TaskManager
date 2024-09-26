package edu.taskmanager.taskmanager.repositories;

import edu.taskmanager.taskmanager.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.taskmanager.taskmanager.domain.user.User;

import java.util.Optional;

/*
* Interface userRepository
* Repositório de usuários que estende JpaRepository para realizar operações de CRUD
 */
public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> findByEmail(String login);

    Optional<User> findById(String id);
}
