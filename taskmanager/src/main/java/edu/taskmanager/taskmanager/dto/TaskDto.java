package edu.taskmanager.taskmanager.dto;

import java.util.Date;

public record TaskDto (String title, String description, String status, Long createdDate, String category, String email){
}
