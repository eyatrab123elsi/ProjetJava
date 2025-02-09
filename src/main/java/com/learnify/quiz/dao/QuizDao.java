package com.learnify.quiz.dao;

import com.learnify.quiz.model.Quiz;
import java.util.List;

public interface QuizDao {
    List<Quiz> getAllQuizzes();
}