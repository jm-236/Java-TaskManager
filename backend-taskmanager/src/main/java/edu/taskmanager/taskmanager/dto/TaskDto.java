package edu.taskmanager.taskmanager.dto;

import edu.taskmanager.taskmanager.domain.task.Task;

import java.util.Date;
import java.util.UUID;

public record TaskDto (String title, String description, String status, Long createdDate, String category,
                       String email, UUID uuid){
    public TaskDto(Task task) {
        this(task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedDate(),
                task.getCategory(),
                task.getUser().getEmail(),
                task.getUuid()
        );
    }

}
