package com.learnify.quiz.controller;

import com.learnify.quiz.model.Question;
import com.learnify.quiz.model.Quiz;
import com.learnify.quiz.service.QuestionService;
import com.learnify.quiz.service.QuestionServiceImpl;
import com.learnify.quiz.service.QuizService;
import com.learnify.quiz.service.QuizServiceImpl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.util.Arrays;
import java.util.List;

public class CreateQuestionController {
    @FXML private ComboBox<Quiz> quizComboBox;
    @FXML private TextField questionTextField;
    @FXML private TextField optionATextField;
    @FXML private TextField optionBTextField;
    @FXML private TextField optionCTextField;
    @FXML private TextField optionDTextField;
    @FXML private ComboBox<String> correctAnswerComboBox;
    
    private final QuestionService questionService = new QuestionServiceImpl();
    private final QuizService quizService = new QuizServiceImpl();

    @FXML
    public void initialize() {
        correctAnswerComboBox.setItems(FXCollections.observableArrayList(Arrays.asList("A", "B", "C", "D")));
        correctAnswerComboBox.getSelectionModel().selectFirst();
        
        List<Quiz> quizzes = quizService.getAllQuizzes();
        quizComboBox.setItems(FXCollections.observableArrayList(quizzes));
    }

    @FXML
    void handleSaveQuestion(ActionEvent event) {
        Quiz selectedQuiz = quizComboBox.getValue();
        if(selectedQuiz == null) {
            System.out.println("Please select a quiz first!");
            return;
        }

        Question question = new Question();
        question.setQuizId(selectedQuiz.getQuizId());
        question.setQuestionText(questionTextField.getText());
        question.setOptionA(optionATextField.getText());
        question.setOptionB(optionBTextField.getText());
        question.setOptionC(optionCTextField.getText());
        question.setOptionD(optionDTextField.getText());
        question.setCorrectAnswerIndex(correctAnswerComboBox.getSelectionModel().getSelectedIndex());

        questionService.createQuestion(question);
        clearFields();
    }

    private void clearFields() {
        questionTextField.clear();
        optionATextField.clear();
        optionBTextField.clear();
        optionCTextField.clear();
        optionDTextField.clear();
    }
}