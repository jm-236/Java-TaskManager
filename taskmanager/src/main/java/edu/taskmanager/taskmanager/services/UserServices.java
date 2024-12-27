package edu.taskmanager.taskmanager.services;

import org.springframework.stereotype.Service;

@Service
public interface UserServices {

    void deleteUser(String email);

    void updatePassword();

    void updateEmail();
}
