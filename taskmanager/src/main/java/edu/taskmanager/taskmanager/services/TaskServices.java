package edu.taskmanager.taskmanager.services;


import edu.taskmanager.taskmanager.domain.task.Task;
import edu.taskmanager.taskmanager.dto.TaskDto;
import org.springframework.stereotype.Service;

@Service
public interface TaskServices {
    Iterable<Task> listAllTasks(String userId);

    void saveTask(TaskDto taskdto, String authHeader);

    void deleteTask(String taskId);

    void updateTask(String taskId, Task task);

    String getTaskName(String taskId);
}
