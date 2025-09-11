package edu.taskmanager.taskmanager.services.impl;

import edu.taskmanager.taskmanager.domain.task.Task;
import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.dto.TaskDto;
import edu.taskmanager.taskmanager.infra.security.TokenService;
import edu.taskmanager.taskmanager.repositories.TaskRepository;
import edu.taskmanager.taskmanager.repositories.UserRepository;
import edu.taskmanager.taskmanager.services.TaskServices;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * TaskServicesImpl is a service implementation class that provides methods for managing tasks.
 * It implements the TaskServices interface.
 */
@Service
@RequiredArgsConstructor
public class TaskServicesImpl implements TaskServices {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final TokenService tokenService;

    /**
     * Lists all tasks associated with the user identified by the authorization token.
     * @param authentication - The authentication status.
     * @return a list of tasks associated with the user.
     * @throws EntityNotFoundException if the user is not found.
     */
    @Override
    public List<TaskDto> listAllTasks(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Optional<List<Task>> listOpt = taskRepository.findByUser(user);
        if (listOpt.isPresent()){
            return listOpt.get().stream().map(TaskDto::new)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
//        throw new EntityNotFoundException("User not found with email " + userEmail);
    }

    /**
     * Saves a new task based on the provided TaskDto and authorization token.
     * @param taskDto - TaskDto object that contains the task details.
     * @throws EntityNotFoundException if the user is not found.
     */
    @Override
    public void saveTask(TaskDto taskDto, Authentication authentication) {

        try {
            User user = (User) authentication.getPrincipal();

            Task newTask = new Task();
            newTask.setTitle(taskDto.title());
            newTask.setDescription(taskDto.description());
            newTask.setStatus(taskDto.status());
            newTask.setCategory(taskDto.category());
            newTask.setCreatedDate(taskDto.createdDate());
            newTask.setUser(user);
            taskRepository.save(newTask);
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    /**
     * Updates an existing task based on the provided TaskDto, taskId, and authorization token.
     * @param taskDto - TaskDto object that contains the updated task details.
     * @param taskId - The ID of the task to be updated.
     * @param authHeader - The authorization token from the request header.
     * @throws EntityNotFoundException if the user or task is not found.
     */
    @Override
    public void updateTask(TaskDto taskDto, UUID taskId, Authentication authentication) {
        Task newTask = new Task();
        newTask.setTitle(taskDto.title());
        newTask.setDescription(taskDto.description());
        newTask.setStatus(taskDto.status());
        newTask.setCategory(taskDto.category());
        newTask.setCreatedDate(taskDto.createdDate());
        User user = (User) authentication.getPrincipal();
        newTask.setUser(user);

        Optional<Task> taskOptional = taskRepository.findByUuid(taskId);

        if (taskOptional.isPresent()) {
            newTask.setId(taskOptional.get().getId());
            taskRepository.save(newTask);
        } else {
            throw new EntityNotFoundException("Task not found id " + taskId + ".");
        }
    }

    private String recoverTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWTCookie".equals(cookie.getName())) {
                    return "Bearer " + cookie.getValue();
                }
            }
        }
        return null;
    }
}