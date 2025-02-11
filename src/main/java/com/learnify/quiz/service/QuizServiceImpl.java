package com.learnify.quiz.service;

import com.learnify.quiz.dao.QuizDao;
import com.learnify.quiz.dao.QuizDaoImpl;
import com.learnify.quiz.model.Quiz;
import java.util.List;

public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao = new QuizDaoImpl();

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizDao.getAllQuizzes();
    }
}