package edu.taskmanager.taskmanager.repositories;

import edu.taskmanager.taskmanager.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.taskmanager.taskmanager.domain.user.User;

import java.util.Optional;

/**
 * UserRepository is a repository interface for managing User entities.
 * It extends JpaRepository to provide CRUD operations and additional query methods.
 */
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Finds a user by their email.
     * @param login - The email of the user.
     * @return an Optional containing the user if found, or empty if not found.
     */
    Optional<User> findByEmail(String login);

    /**
     * Finds a user by their unique identifier.
     * @param id - The unique identifier of the user.
     * @return an Optional containing the user if found, or empty if not found.
     */
    Optional<User> findById(String id);
}