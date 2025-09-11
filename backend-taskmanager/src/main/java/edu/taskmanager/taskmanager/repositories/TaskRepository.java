package edu.taskmanager.taskmanager.repositories;

import edu.taskmanager.taskmanager.domain.task.Task;
import edu.taskmanager.taskmanager.domain.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * TaskRepository is a repository interface for managing Task entities.
 * It extends JpaRepository to provide CRUD operations and additional query methods.
 */
public interface TaskRepository extends JpaRepository<Task, String> {

    /**
     * Finds a list of tasks associated with a specific user.
     * @param user - The user whose tasks are to be retrieved.
     * @return an Optional containing a list of tasks associated with the user.
     */
    Optional<List<Task>> findByUser(User user);

    /**
     * Finds a task by its unique identifier.
     * @param id - The unique identifier of the task.
     * @return an Optional containing the task if found, or empty if not found.
     */
    Optional<Task> findByUuid(UUID uuid);

    /**
     * Deletes a task entity.
     * @param entity - The task entity to be deleted.
     */
    void delete(Task entity);
}