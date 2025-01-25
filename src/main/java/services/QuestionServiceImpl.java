package services;

import dao.QuestionDao;
import dao.QuestionDaoImpl;
import entities.Question;

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