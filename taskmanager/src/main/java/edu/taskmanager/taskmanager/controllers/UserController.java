package edu.taskmanager.taskmanager.controllers;

import edu.taskmanager.taskmanager.domain.task.Task;
import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.dto.TaskDto;
import edu.taskmanager.taskmanager.repositories.TaskRepository;
import edu.taskmanager.taskmanager.repositories.UserRepository;
import edu.taskmanager.taskmanager.services.TaskServices;
import edu.taskmanager.taskmanager.services.impl.TaskServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("SUCESSO!");
    }

    @GetMapping("/tasks")
    public ResponseEntity<String> getTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Task> tasks = (List<Task>) taskServices.listAllTasks(username);

        return ResponseEntity.ok(tasks.toString());
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody TaskDto body){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Task newTask = new Task();
        newTask.setTitle(body.title());
        newTask.setDescription(body.description());
        newTask.setStatus(body.status());
        newTask.setCategory(body.category());
        newTask.setCreatedDate(body.createdDate());

        taskServices.saveTask(newTask, username);

        return ResponseEntity.ok("Task created!");
    }
}
