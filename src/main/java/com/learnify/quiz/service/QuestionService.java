package com.learnify.quiz.service;

import com.learnify.quiz.model.Question;
import java.util.List;

public interface QuestionService {
    void createQuestion(Question question);
    List<Question> getAllQuestions();
    List<Question> getQuestionsByQuizId(int quizId);
    void updateQuestion(Question question);
    void deleteQuestion(int questionId);
}