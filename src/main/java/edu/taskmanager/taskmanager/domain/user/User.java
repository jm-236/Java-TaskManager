package edu.taskmanager.taskmanager.domain.user;

import edu.taskmanager.taskmanager.domain.task.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * User is an entity that represents a user in the system.
 * It is annotated with @Entity, meaning it is a JPA entity.
 * It is also annotated with @Table(name = "users"), meaning it is mapped to the "users" table in the database.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * The unique identifier for the user.
     * It is annotated with @Id, meaning it is the primary key.
     * It is also annotated with @GeneratedValue(strategy = GenerationType.UUID), meaning its value is automatically generated using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // id: identificador do usuário

    /**
     * The list of tasks associated with the user.
     * It is annotated with @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER), meaning it is a one-to-many relationship and is eagerly fetched.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Task> tarefas;

    /**
     * The name of the user.
     */
    private String name; // name: nome do usuário

    /**
     * The email of the user.
     */
    private String email; // email: email do usuário

    /**
     * The password of the user.
     */
    private String password; // password: senha do usuário

    /**
     * Adds a task to the user's list of tasks.
     * @param task - The task to be added.
     */
    public void addTask(Task task) {
        tarefas.add(task);
        task.setUser(this); // Define a relação bidirecional
    }

    /**
     * Removes a task from the user's list of tasks.
     * @param task - The task to be removed.
     */
    public void removeTask(Task task) {
        tarefas.remove(task);
        task.setUser(null); // Remove a referência do usuário na tarefa
    }

    /**
     * Returns a string representation of the user.
     * @return a string representation of the user.
     */
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}