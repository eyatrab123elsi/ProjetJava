package com.learnify.quiz.controller;

import com.learnify.quiz.model.Question;
import com.learnify.quiz.service.QuestionService;
import com.learnify.quiz.service.QuestionServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;

public class EditQuestionController {

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
    @FXML
    private ComboBox<Question> questionComboBox;
    private Question selectedQuestion;
    private final QuestionService questionService = new QuestionServiceImpl();


    @FXML
    public void initialize() {
        correctAnswerComboBox.setItems(FXCollections.observableArrayList(Arrays.asList("A", "B", "C", "D")));
        loadQuestionData();
        questionComboBox.setOnAction(this::handleQuestionSelection);
    }
     private void loadQuestionData() {
        List<Question> questions = questionService.getAllQuestions();
         ObservableList<Question> observableList = FXCollections.observableArrayList(questions);
        questionComboBox.setItems(observableList);
    }

    private void handleQuestionSelection(ActionEvent event) {
      selectedQuestion = questionComboBox.getValue();
       if(selectedQuestion != null) {
         questionTextField.setText(selectedQuestion.getQuestionText());
         optionATextField.setText(selectedQuestion.getOptionA());
         optionBTextField.setText(selectedQuestion.getOptionB());
         optionCTextField.setText(selectedQuestion.getOptionC());
         optionDTextField.setText(selectedQuestion.getOptionD());
         correctAnswerComboBox.getSelectionModel().select(selectedQuestion.getCorrectAnswerIndex());
      }
   }

    @FXML
    void handleSaveQuestion(ActionEvent event) {
      if (selectedQuestion == null) {
        System.out.println("Select a question first.");
        return;
      }
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
       // Update the selected question with new values
       selectedQuestion.setQuestionText(questionText);
       selectedQuestion.setOptionA(optionA);
       selectedQuestion.setOptionB(optionB);
       selectedQuestion.setOptionC(optionC);
       selectedQuestion.setOptionD(optionD);
       selectedQuestion.setCorrectAnswerIndex(correctAnswerIndex);

        questionService.updateQuestion(selectedQuestion);

        System.out.println("Question Updated");
        questionTextField.clear();
        optionATextField.clear();
        optionBTextField.clear();
        optionCTextField.clear();
        optionDTextField.clear();
    }
}