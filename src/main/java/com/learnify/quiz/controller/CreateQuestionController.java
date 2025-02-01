package com.learnify.quiz.controller;

import com.learnify.quiz.model.Question;
import com.learnify.quiz.service.QuestionService;
import com.learnify.quiz.service.QuestionServiceImpl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Arrays;


public class CreateQuestionController {
        @FXML
        private TextField questionTextField;

        @FXML
        private TextField optionATextField;

        @FXML
        private TextField optionBTextField;

        @FXML
        private TextField optionCTextField;

        @FXML
        private TextField optionDTextField;

        @FXML
        private ComboBox<String> correctAnswerComboBox;

        private final QuestionService questionService = new QuestionServiceImpl();

        @FXML
        public void initialize() {
            correctAnswerComboBox.setItems(FXCollections.observableArrayList(Arrays.asList("A", "B", "C", "D")));
            correctAnswerComboBox.getSelectionModel().selectFirst(); //set default value
        }


        @FXML
        void handleSaveQuestion(ActionEvent event) {
            String questionText = questionTextField.getText();
            String optionA = optionATextField.getText();
            String optionB = optionBTextField.getText();
            String optionC = optionCTextField.getText();
            String optionD = optionDTextField.getText();
            int correctAnswerIndex = correctAnswerComboBox.getSelectionModel().getSelectedIndex();

            if (questionText.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty()) {
                System.out.println("All Fields Required");
                return;
            }

            Question question = new Question();
            question.setQuestionText(questionText);
            question.setOptionA(optionA);
            question.setOptionB(optionB);
            question.setOptionC(optionC);
            question.setOptionD(optionD);
            question.setCorrectAnswerIndex(correctAnswerIndex);

            questionService.createQuestion(question);
            System.out.println("Question Saved!");

            questionTextField.clear();
            optionATextField.clear();
            optionBTextField.clear();
            optionCTextField.clear();
            optionDTextField.clear();
        }
   }