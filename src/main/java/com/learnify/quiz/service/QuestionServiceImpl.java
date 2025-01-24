package com.learnify.quiz.service;

import com.learnify.quiz.dao.QuestionDao;
import com.learnify.quiz.dao.QuestionDaoImpl;
import com.learnify.quiz.model.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao = new QuestionDaoImpl();

    @Override
    public void createQuestion(Question question) {
        questionDao.createQuestion(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionDao.getAllQuestions();
    }
}