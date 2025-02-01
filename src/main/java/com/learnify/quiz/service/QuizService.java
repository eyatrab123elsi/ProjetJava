package com.learnify.quiz.service;

import com.learnify.quiz.model.Quiz;
import java.util.List;

public interface QuizService {
    void createQuiz(Quiz quiz);
    List<Quiz> getAllQuizzes();
    void updateQuiz(Quiz quiz);
    void deleteQuiz(int quizId);
}