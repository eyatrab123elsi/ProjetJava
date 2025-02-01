package com.learnify.quiz.service;
import com.learnify.quiz.dao.UserDao;
import com.learnify.quiz.dao.UserDaoImpl;
import com.learnify.quiz.model.User;
import java.util.List;
public class UserServiceImpl implements UserService {
   private final UserDao userDao = new UserDaoImpl();
    @Override
    public void createUser(User user) {
        userDao.createUser(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}