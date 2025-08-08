package edu.taskmanager.taskmanager.services.impl;

import edu.taskmanager.taskmanager.domain.task.Task;
import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.repositories.TaskRepository;
import edu.taskmanager.taskmanager.repositories.UserRepository;
import edu.taskmanager.taskmanager.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the UserServices interface.
 * Provides user-related operations such as deleting a user.
 */
@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {


    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    /**
     * Deletes a user by their email.
     * If the user has associated tasks, they are also deleted.
     *
     * @param email the email of the user to be deleted
     */
    @Override
    public void deleteUser(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Optional<List<Task>> listOpt = taskRepository.findByUser(user);

            if (listOpt.isPresent()){
                taskRepository.deleteAll(listOpt.get());
            }
            userRepository.delete(user);
        }
    }
}
