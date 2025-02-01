package com.learnify.quiz.dao;

import com.learnify.quiz.model.Quiz;
import java.util.List;

public interface QuizDao {
    void createQuiz(Quiz quiz);
    List<Quiz> getAllQuizzes();
    void updateQuiz(Quiz quiz);
    void deleteQuiz(int quizId);
}