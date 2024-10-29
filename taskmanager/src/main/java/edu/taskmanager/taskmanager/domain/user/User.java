package edu.taskmanager.taskmanager.domain.user;

import edu.taskmanager.taskmanager.domain.task.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/*
* Entidade User
* Representa um usuário do sistema
*/
@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // id identificador do usuário

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Task> tarefas;

    private String name; // name: nome do usuário
    private String email; // email: email do usuário
    private String password; // password: senha do usuário

    // Método para adicionar uma tarefa à lista de tarefas do usuário
    public void addTask(Task task) {
        tarefas.add(task);
        task.setUser(this); // Define a relação bidirecional
    }

    public void removeTask(Task task) {
        tarefas.remove(task);
        task.setUser(null); // Remove a referência do usuário na tarefa
    }

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
