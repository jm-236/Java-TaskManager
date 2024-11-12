package edu.taskmanager.taskmanager.services.impl;

import edu.taskmanager.taskmanager.domain.task.Task;
import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.dto.TaskDto;
import edu.taskmanager.taskmanager.infra.security.TokenService;
import edu.taskmanager.taskmanager.repositories.TaskRepository;
import edu.taskmanager.taskmanager.repositories.UserRepository;
import edu.taskmanager.taskmanager.services.TaskServices;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * TaskServicesImpl is a service implementation class that provides methods for managing tasks.
 * It implements the TaskServices interface.
 */
@Service
public class TaskServicesImpl implements TaskServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    TokenService tokenService;

    /**
     * Lists all tasks associated with the user identified by the authorization token.
     * @param authorizationHeader - The authorization token from the request header.
     * @return a list of tasks associated with the user.
     * @throws EntityNotFoundException if the user is not found.
     */
    @Override
    public List<Task> listAllTasks(String authorizationHeader) {

        String userEmail = tokenService.getUserEmailFromToken(authorizationHeader);

        Optional<User> userOpt = userRepository.findByEmail(userEmail);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Optional<List<Task>> listOpt = taskRepository.findByUser(user);
            if (listOpt.isPresent()){
                return listOpt.get();
            }
            return null;
        }
        throw new EntityNotFoundException("User not found with email " + userEmail);
    }

    /**
     * Saves a new task based on the provided TaskDto and authorization token.
     * @param taskDto - TaskDto object that contains the task details.
     * @param authHeader - The authorization token from the request header.
     * @throws EntityNotFoundException if the user is not found.
     */
    @Override
    public void saveTask(TaskDto taskDto, String authHeader) {

        String email = tokenService.getUserEmailFromToken(authHeader);

        Task newTask = new Task();
        newTask.setTitle(taskDto.title());
        newTask.setDescription(taskDto.description());
        newTask.setStatus(taskDto.status());
        newTask.setCategory(taskDto.category());
        newTask.setCreatedDate(taskDto.createdDate());

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            newTask.setUser(user);
            taskRepository.save(newTask);

        }
        else {
            throw new EntityNotFoundException("User not found with email " + email);
        }
    }

    /**
     * Deletes a task identified by the task ID.
     * @param taskId - The ID of the task to be deleted.
     */
    @Override
    @Transactional
    public void deleteTask(String taskId) {

        Optional<Task> taskOpt = taskRepository.findById(taskId);

        if (taskOpt.isPresent()){
            Task task = taskOpt.get();
            User user = task.getUser();
            user.removeTask(task);
            taskRepository.delete(taskOpt.get());
            System.out.println("Task deleted: " + taskOpt.get());
        }
    }

    /**
     * Updates an existing task based on the provided TaskDto, taskId, and authorization token.
     * @param taskDto - TaskDto object that contains the updated task details.
     * @param taskId - The ID of the task to be updated.
     * @param authHeader - The authorization token from the request header.
     * @throws EntityNotFoundException if the user or task is not found.
     */
    @Override
    public void updateTask(TaskDto taskDto, String taskId, String authHeader) {
        Task newTask = new Task();
        newTask.setTitle(taskDto.title());
        newTask.setDescription(taskDto.description());
        newTask.setStatus(taskDto.status());
        newTask.setCategory(taskDto.category());
        newTask.setCreatedDate(taskDto.createdDate());

        String email = tokenService.getUserEmailFromToken(authHeader);
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()){
            User user = userOptional.get();
            newTask.setUser(user);
        }
        else {
            throw new EntityNotFoundException("Found no user with email " + email);
        }

        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isPresent()) {
            newTask.setId(taskId);
            taskRepository.save(newTask);
        }
        else {
            throw new EntityNotFoundException("Task not found with id " + taskId);
        }
    }

    /**
     * Retrieves the name of the task identified by the task ID.
     * @param taskId - The ID of the task.
     * @return the name of the task.
     * @throws EntityNotFoundException if the task is not found.
     */
    @Override
    public String getTaskName(String taskId){
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isPresent()) {
            return taskOptional.get().getTitle();
        }
        else {
            throw new EntityNotFoundException("Task not found with id " + taskId);
        }
    }
}