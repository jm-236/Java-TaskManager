package edu.taskmanager.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.taskmanager.taskmanager.domain.user.User;
/*
* Interface userRepository
* Repositório de usuários que estende JpaRepository para realizar operações de CRUD
 */
public interface userRepository extends JpaRepository<User, String>{
}
