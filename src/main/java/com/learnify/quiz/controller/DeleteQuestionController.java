package com.learnify.quiz.controller;

import com.learnify.quiz.model.Question;
import com.learnify.quiz.service.QuestionService;
import com.learnify.quiz.service.QuestionServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.util.List;


public class DeleteQuestionController {

    @FXML
    private TextArea questionTextArea;
     @FXML
    private ComboBox<Question> questionComboBox;
    private Question selectedQuestion;
    private final QuestionService questionService = new QuestionServiceImpl();

    @FXML
    public void initialize() {
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
      if (selectedQuestion != null) {
          questionTextArea.setText(selectedQuestion.getQuestionText());
       }
    }
    @FXML
    void handleDeleteQuestion(ActionEvent event) {
      if(selectedQuestion == null) {
          System.out.println("Select a question first");
         return;
       }

        questionService.deleteQuestion(selectedQuestion.getQuestionId());

        System.out.println("Question Deleted");
         questionTextArea.clear();
    }
}