package com.learnify.quiz.dao;

import com.learnify.quiz.model.Question;
import java.util.List;

public interface QuestionDao {
    void createQuestion(Question question);
    List<Question> getAllQuestions();
    List<Question> getQuestionsByQuizId(int quizId);  // New method
    void updateQuestion(Question question);
    void deleteQuestion(int questionId);
}