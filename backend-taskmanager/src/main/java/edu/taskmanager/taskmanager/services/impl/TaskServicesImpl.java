package edu.taskmanager.taskmanager.services.impl;

import edu.taskmanager.taskmanager.domain.task.Task;
import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.dto.TaskDto;
import edu.taskmanager.taskmanager.infra.security.TokenService;
import edu.taskmanager.taskmanager.repositories.TaskRepository;
import edu.taskmanager.taskmanager.repositories.UserRepository;
import edu.taskmanager.taskmanager.services.TaskServices;
import edu.taskmanager.taskmanager.specification.TaskSpecifications;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<TaskDto> listAllTasks(
            Authentication authentication,
            String query,
            String parameterSearch,
            Pageable pageable
            ) {

        User user = (User) authentication.getPrincipal();
        Specification<Task> spec = TaskSpecifications.searchGlobal(user, query, parameterSearch);


        // O Spring Data JPA faz a query dinâmica e a paginação automaticamente
        Page<Task> tasksPage = taskRepository.findAll(spec, pageable);

        // Convertemos a página de Entidade para página de DTO
        return tasksPage.map(TaskDto::new).stream().toList();
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
    public void deleteTask(UUID taskId, Authentication authentication) {

        Optional<Task> taskOpt = taskRepository.findByUuid(taskId);
        User user = (User) authentication.getPrincipal();

        if (taskOpt.isPresent()){
            Task task = taskOpt.get();
            User taskUser = task.getUser();
            if (!taskUser.getId().equals(user.getId())) {
                throw new SecurityException("User does not have permission to delete this task.");
            }
            taskUser.removeTask(task);
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
    public String getTaskName(UUID taskId){
        Optional<Task> taskOptional = taskRepository.findByUuid(taskId);

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
     * @throws EntityNotFoundException if the user or task is not found.
     */
    @Override
    public void updateTask(TaskDto taskDto, UUID taskId, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Task taskToUpdate = taskRepository.findByUuid(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with uuid " + taskId));

        if (!taskToUpdate.getUser().getId().equals(user.getId())) {
            throw new SecurityException("User does not have permission to update this task.");
        }

        taskToUpdate.setTitle(taskDto.title());
        taskToUpdate.setDescription(taskDto.description());
        taskToUpdate.setStatus(taskDto.status());
        taskToUpdate.setCategory(taskDto.category());

        taskRepository.save(taskToUpdate);
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