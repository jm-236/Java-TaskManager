package edu.taskmanager.taskmanager.services.impl;

import edu.taskmanager.taskmanager.domain.task.Task;
import edu.taskmanager.taskmanager.domain.user.User;
import edu.taskmanager.taskmanager.repositories.TaskRepository;
import edu.taskmanager.taskmanager.repositories.UserRepository;
import edu.taskmanager.taskmanager.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServicesImpl implements TaskServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Iterable<Task> listAllTasks(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(value -> taskRepository.findByUser(value).orElse(null)).orElse(null);
    }

    @Override
    public Iterable<Task> listAllTasksByCategory(String userId, String category) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Task> userTasks = taskRepository.findByUser(userOptional.get())
                    .orElse(null);
            if (userTasks != null) {
                return userTasks.stream().filter(task -> task.getCategory().equals(category)).toList();
            }
        }
        return null;
    }

    @Override
    public void saveTask(Task task, String userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            user.get().addTask(task);
            taskRepository.save(task);
        }
    }

    @Override
    public void deleteTask(String taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        Optional<User> userOptional = userRepository.findById(taskOptional.get().getUser().getId());

        if (userOptional.isPresent()) {
            Task task = taskOptional.get();
            User user = userOptional.get();

            user.removeTask(task);
            task.setUser(null);
        }
    }

    @Override
    public void updateTask(String taskId, Task task) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isPresent()) {
            Task taskToUpdate = taskOptional.get();
            taskToUpdate.setTitle(task.getTitle());
            taskToUpdate.setDescription(task.getDescription());
            taskToUpdate.setCreatedDate(task.getCreatedDate());
            taskToUpdate.setCategory(task.getCategory());
            taskToUpdate.setStatus(task.getStatus());
            taskRepository.save(taskToUpdate);
        }
    }
}


