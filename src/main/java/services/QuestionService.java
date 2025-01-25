package services;

import entities.Question;
import java.util.List;

public interface QuestionService {
    void createQuestion(Question question);
    List<Question> getAllQuestions();
}