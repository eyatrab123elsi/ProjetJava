package controllers;

import entities.Question;
import services.QuestionService;
import services.QuestionServiceImpl;

public class CreateQuestionController {
    private final QuestionService questionService = new QuestionServiceImpl();

    public void saveQuestion(String text, String optionA, String optionB, String optionC, String optionD, int correctIndex) {
        Question question = new Question();
        question.setQuestionText(text);
        question.setOptionA(optionA);
        question.setOptionB(optionB);
        question.setOptionC(optionC);
        question.setOptionD(optionD);
        question.setCorrectAnswerIndex(correctIndex);
        questionService.createQuestion(question);
        System.out.println("Question saved successfully!");
    }
}