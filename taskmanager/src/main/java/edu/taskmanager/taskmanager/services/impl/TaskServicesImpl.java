package edu.taskmanager.taskmanager.services.impl;

import edu.taskmanager.taskmanager.domain.task.Task;
import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.infra.security.TokenService;
import edu.taskmanager.taskmanager.repositories.TaskRepository;
import edu.taskmanager.taskmanager.repositories.UserRepository;
import edu.taskmanager.taskmanager.services.TaskServices;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServicesImpl implements TaskServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    TokenService tokenService;

    @Override
    public List<Task> listAllTasks(String userEmail) {
        Optional<User> userOpt = userRepository.findByEmail(userEmail);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Optional<List<Task>> listOpt = taskRepository.findByUser(user);
            if (listOpt.isPresent()){
                return listOpt.get();
            }
            return null;
        }
        return null;
    }

//    @Override
//    public Iterable<Task> listAllTasksByCategory(String userId, String category) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            List<Task> userTasks = taskRepository.findByUser(userOptional.get())
//                    .orElse(null);
//            if (userTasks != null) {
//                return userTasks.stream().filter(task -> task.getCategory().equals(category)).toList();
//            }
//        }
//        return null;
//    }

    @Override
    public void saveTask(Task task, String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            task.setUser(user);
            taskRepository.save(task);

        }
        else {
            throw new EntityNotFoundException("User not found with email " + email);
        }
    }

//    @Override
//    public void deleteTask(String taskId) {
//        Optional<Task> taskOptional = taskRepository.findById(taskId);
//        Optional<User> userOptional = userRepository.findById(taskOptional.get().getUser().getId());
//
//        if (userOptional.isPresent()) {
//            Task task = taskOptional.get();
//            User user = userOptional.get();
//
//            user.removeTask(task);
//            task.setUser(null);
//        }
//    }

    @Override
    public void updateTask(String taskId, Task task) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isPresent()) {
            task.setId(taskId);
            taskRepository.save(task);
        }
        else {
            throw new EntityNotFoundException("Task not found with id " + taskId);
        }
    }
}


