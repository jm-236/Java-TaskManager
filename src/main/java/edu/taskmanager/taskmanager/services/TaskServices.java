package edu.taskmanager.taskmanager.services;

import edu.taskmanager.taskmanager.domain.task.Task;
import edu.taskmanager.taskmanager.dto.TaskDto;
import org.springframework.stereotype.Service;

/**
 * TaskServices is a service interface that provides methods for managing tasks.
 */
@Service
public interface TaskServices {
    /**
     * Lists all tasks associated with the user identified by the user ID.
     * @param userId - The ID of the user whose tasks are to be listed.
     * @return an Iterable containing all tasks associated with the user.
     */
    Iterable<Task> listAllTasks(String userId);

    /**
     * Saves a new task based on the provided TaskDto and authorization token.
     * @param taskdto - TaskDto object that contains the task details.
     * @param authHeader - The authorization token from the request header.
     */
    void saveTask(TaskDto taskdto, String authHeader);

    /**
     * Deletes a task identified by the task ID.
     * @param taskId - The ID of the task to be deleted.
     */
    void deleteTask(String taskId);

    /**
     * Updates an existing task based on the provided TaskDto, taskId, and authorization token.
     * @param taskDto - TaskDto object that contains the updated task details.
     * @param taskId - The ID of the task to be updated.
     * @param authHeader - The authorization token from the request header.
     */
    void updateTask(TaskDto taskDto, String taskId, String authHeader);

    /**
     * Retrieves the name of the task identified by the task ID.
     * @param taskId - The ID of the task.
     * @return the name of the task.
     */
    String getTaskName(String taskId);
}