package com.learnify.quiz.service;

import com.learnify.quiz.model.Question;
import java.util.List;

public interface QuestionService {
    void createQuestion(Question question);
    List<Question> getAllQuestions();
    void updateQuestion(Question question); //Added
    void deleteQuestion(int questionId);   //Added
}