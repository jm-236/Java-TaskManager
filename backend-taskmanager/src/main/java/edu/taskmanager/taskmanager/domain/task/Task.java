package edu.taskmanager.taskmanager.domain.task;

import edu.taskmanager.taskmanager.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

/**
 * Task is an entity that represents a task in the system.
 * It is annotated with @Entity, meaning it is a JPA entity.
 * It is also annotated with @Table(name = "tasks"), meaning it is mapped to the "tasks" table in the database.
 */
@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    /**
     * The unique identifier for the task.
     * It is annotated with @Id, meaning it is the primary key.
     * It is also annotated with @GeneratedValue(strategy = GenerationType.UUID), meaning its value is automatically generated using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id; // id: identificador da tarefa

    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid; // Identificador PÚBLICO

    /**
     * The user who created the task.
     * It is annotated with @ManyToOne(fetch = FetchType.EAGER), meaning it is a many-to-one relationship and is eagerly fetched.
     * It is also annotated with @JoinColumn(name = "user_id"), meaning it is joined on the "user_id" column.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user; // userId: identificador do usuário que criou a tarefa

    /**
     * The title of the task.
     */
    private String title; // title: título da tarefa

    /**
     * The description of the task.
     */
    private String description; // description: descrição da tarefa

    /**
     * The status of the task.
     */
    private String status; // status: status da tarefa

    /**
     * The date the task was created.
     */
    private Date createdDate; // createdDate: data de criação da tarefa

    /**
     * The category of the task.
     */
    private String category; // category: categoria da tarefa

    /**
     * Returns a string representation of the task.
     * @return a string representation of the task.
     */
    @Override
    public String toString() {
        return "Task{" +
                "Id='" + Id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdDate=" + createdDate +
                ", category='" + category + '\'' +
                '}';
    }

    @PrePersist
    public void generateUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }
}