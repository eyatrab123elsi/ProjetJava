package com.learnify.quiz.dao;

import com.learnify.quiz.model.User;
import java.util.List;
public interface UserDao {
    void createUser(User user);
    List<User> getAllUsers();
}