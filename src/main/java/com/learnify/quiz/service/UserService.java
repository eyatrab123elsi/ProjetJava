package com.learnify.quiz.service;

import com.learnify.quiz.model.User;
import java.util.List;
public interface UserService {
    void createUser(User user);
    List<User> getAllUsers();
}