package dao;

import entities.Question;
import java.util.List;

public interface QuestionDao {
    void createQuestion(Question question);
    List<Question> getAllQuestions();
}