package edu.taskmanager.taskmanager.controllers;

import edu.taskmanager.taskmanager.domain.task.Task;
import edu.taskmanager.taskmanager.dto.TaskDto;
import edu.taskmanager.taskmanager.infra.security.TokenService;
import edu.taskmanager.taskmanager.services.impl.TaskServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController is a REST controller that handles HTTP requests related to users.
 * It is annotated with @RestController, meaning it's a controller where every method returns a domain object instead of a view.
 * It's also annotated with @RequestMapping("/user"), meaning all HTTP requests that match "/user" will be handled by this controller.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private TaskServicesImpl taskServices;

    @Autowired
    private TokenService tokenService;

    /**
     * This method is a simple GET endpoint.
     * It is annotated with @GetMapping, meaning it will respond to HTTP GET requests.
     * When a GET request is made to "/user", this method will be invoked.
     * It returns a ResponseEntity with the body "SUCESSO!" and a HTTP status code of 200 (OK).
     * ResponseEntity is a real deal. It represents the entire HTTP response. Good thing about it is that you can control anything that goes into it.
     * @return a ResponseEntity with the body "SUCESSO!" and a HTTP status code of 200 (OK).
     */
    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("SUCESSO!");
    }

    /**
     * This method is a GET endpoint for retrieving tasks.
     * It is annotated with @GetMapping, meaning it will respond to HTTP GET requests.
     * When a GET request is made to "/user/tasks", this method will be invoked.
     * It retrieves a list of tasks associated with the user based on the provided authorization token.
     * @param authorizationHeader - The authorization token from the request header.
     * @return a ResponseEntity with the list of tasks and a HTTP status code of 200 (OK).
     */
    @GetMapping("/tasks")
    public ResponseEntity<String> getTasks(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader == null) {
            return ResponseEntity.ok("Token não encontrado");
        }

        List<Task> tasks = taskServices.listAllTasks(authorizationHeader);
        return new ResponseEntity<>(tasks.toString(), HttpStatus.OK);
    }

    /**
     * This method is a POST endpoint for creating a new task.
     * It is annotated with @PostMapping, meaning it will respond to HTTP POST requests.
     * When a POST request is made to "/user/tasks", this method will be invoked.
     * It creates a new task based on the provided TaskDto and authorization token.
     * @param body - TaskDto object that contains the task details.
     * @param authorizationHeader - The authorization token from the request header.
     * @return a ResponseEntity with a success message and a HTTP status code of 200 (OK).
     */
    @PostMapping("/tasks")
    public ResponseEntity<String> createTask(@RequestBody TaskDto body, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader == null) {
            return ResponseEntity.ok("Token não encontrado");
        }

        taskServices.saveTask(body, authorizationHeader);
        return ResponseEntity.ok("Task created!");
    }

    /**
     * This method is a PUT endpoint for updating an existing task.
     * It is annotated with @PutMapping, meaning it will respond to HTTP PUT requests.
     * When a PUT request is made to "/user/tasks/{taskId}", this method will be invoked.
     * It updates an existing task based on the provided TaskDto, taskId, and authorization token.
     * @param body - TaskDto object that contains the updated task details.
     * @param taskId - The ID of the task to be updated.
     * @param authorizationHeader - The authorization token from the request header.
     * @return a ResponseEntity with a success message and a HTTP status code of 200 (OK).
     */
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<String> updateTask(@RequestBody TaskDto body, @PathVariable String taskId,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader == null) {
            return ResponseEntity.ok("Token não encontrado");
        }

        taskServices.updateTask(body, taskId, authorizationHeader);
        return ResponseEntity.ok("Tarefa atualizada com sucesso!");
    }

    /**
     * This method is a DELETE endpoint for deleting an existing task.
     * It is annotated with @DeleteMapping, meaning it will respond to HTTP DELETE requests.
     * When a DELETE request is made to "/user/tasks/{taskId}", this method will be invoked.
     * It deletes an existing task based on the provided taskId.
     * @param taskId - The ID of the task to be deleted.
     * @return a ResponseEntity with a success message and a HTTP status code of 200 (OK).
     */
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable String taskId){
        String taskName = taskServices.getTaskName(taskId);
        taskServices.deleteTask(taskId);
        return ResponseEntity.ok("Task '" + taskName + "' deleted with sucess");
    }
}
