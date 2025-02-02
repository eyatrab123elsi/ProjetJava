package com.learnify.quiz.dao;

import com.learnify.quiz.model.Question;
import java.util.List;

public interface QuestionDao {
    void createQuestion(Question question);
    List<Question> getAllQuestions();
    void updateQuestion(Question question); //Added
    void deleteQuestion(int questionId);   //Added
}