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

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id; // id: identificador da tarefa

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user; // userId: identificador do usuário que criou a tarefa

    private String title; // title: título da tarefa
    private String description; // description: descrição da tarefa
    private String status; // status: status da tarefa
    private Long createdDate; // createdDate: data de criação da tarefa
    private String category; // category: categoria da tarefa

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
}
