package edu.taskmanager.taskmanager.services;

import edu.taskmanager.taskmanager.dto.TaskDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

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
    Iterable<TaskDto> listAllTasks(
       Authentication authentication,
       String query,
       Pageable pageable
    );

    /**
     * Saves a new task based on the provided TaskDto and authorization token.
     * @param taskdto - TaskDto object that contains the task details.
     * @param authHeader - The authorization token from the request header.
     */
    void saveTask(TaskDto taskdto, Authentication authentication);

    /**
     * Deletes a task identified by the task ID.
     * @param taskId - The ID of the task to be deleted.
     */
    void deleteTask(UUID taskId, Authentication authentication);

    /**
     * Updates an existing task based on the provided TaskDto, taskId, and authorization token.
     * @param taskDto - TaskDto object that contains the updated task details.
     * @param taskId - The ID of the task to be updated.
     * @param authHeader - The authorization token from the request header.
     */
    void updateTask(TaskDto taskDto, UUID taskId, Authentication authentication);

    /**
     * Retrieves the name of the task identified by the task ID.
     * @param taskId - The ID of the task.
     * @return the name of the task.
     */
    String getTaskName(UUID taskId);
}