package edu.taskmanager.taskmanager.repositories;

import edu.taskmanager.taskmanager.domain.task.Task;
import edu.taskmanager.taskmanager.domain.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, String> {
    Optional<List<Task>> findByUser(User user);

    Optional<Task> findById(String id);

    void delete(Task entity);
}
