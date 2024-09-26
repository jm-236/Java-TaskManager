package edu.taskmanager.taskmanager.services;


import edu.taskmanager.taskmanager.domain.task.Task;
import org.springframework.stereotype.Service;

@Service
public interface TaskServices {
    Iterable<Task> listAllTasks(String userId);

    Iterable<Task> listAllTasksByCategory(String userId, String category);

    void saveTask(Task task, String userId);

    void deleteTask(String taskId);

    void updateTask(String taskId, Task task);
}
