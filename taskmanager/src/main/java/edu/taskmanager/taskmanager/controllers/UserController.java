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

    /**
     * This method is a simple GET endpoint.
     * It is annotated with @GetMapping, meaning it will respond to HTTP GET requests.
     * When a GET request is made to "/user", this method will be invoked.
     * It returns a ResponseEntity with the body "SUCESSO!" and a HTTP status code of 200 (OK).
     * ResponseEntity is a real deal. It represents the entire HTTP response. Good thing about it is that you can control anything that goes into it.
     * @return a ResponseEntity with the body "SUCESSO!" and a HTTP status code of 200 (OK).
     */

    @Autowired
    private TaskServicesImpl taskServices;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("SUCESSO!");
    }

    @GetMapping("/tasks")
    public ResponseEntity<String> getTasks(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        if (authorizationHeader == null) {
            return ResponseEntity.ok("Token não encontrado");
        }
        String email = tokenService.getUserEmailFromToken(authorizationHeader);

        List<Task> tasks = taskServices.listAllTasks(email);

        return new ResponseEntity<>(tasks.toString(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String>
    createTask(@RequestBody TaskDto body, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (authorizationHeader == null) {
            return ResponseEntity.ok("Token não encontrado");
        }
        String email = tokenService.getUserEmailFromToken(authorizationHeader);

        Task newTask = new Task();
        newTask.setTitle(body.title());
        newTask.setDescription(body.description());
        newTask.setStatus(body.status());
        newTask.setCategory(body.category());
        newTask.setCreatedDate(body.createdDate());

        taskServices.saveTask(newTask, email);

        return ResponseEntity.ok("Task created!");
    }
}
