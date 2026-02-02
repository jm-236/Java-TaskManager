package edu.taskmanager.taskmanager.dto;

import edu.taskmanager.taskmanager.domain.user.User;

public record UserDto(String name, String email) {
    public UserDto(User user){
        this(
                user.getName(),
                user.getEmail()
        );
    }
}
