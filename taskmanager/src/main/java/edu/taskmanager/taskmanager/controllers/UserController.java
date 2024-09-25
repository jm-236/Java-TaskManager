package edu.taskmanager.taskmanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * UserController is a REST controller that handles HTTP requests related to users.
 * It is annotated with @RestController, meaning it's a controller where every method returns a domain object instead of a view.
 * It's also annotated with @RequestMapping("/user"), meaning all HTTP requests that match "/user" will be handled by this controller.
 */
@RestController
@RequestMapping("/user")
public class UserController {

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
}
