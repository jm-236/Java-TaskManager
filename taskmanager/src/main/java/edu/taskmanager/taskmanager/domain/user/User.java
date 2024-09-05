package edu.taskmanager.taskmanager.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    private String name; // name: nome do usuário
    private String email; // email: email do usuário
    private String password; // password: senha do usuário
}
