package com.learnify.quiz.controller;
import com.learnify.quiz.model.Question;
import com.learnify.quiz.service.QuestionService;
import com.learnify.quiz.service.QuestionServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class QuizListController {
    @FXML
    private TableView<Question> questionTableView;

    @FXML
    private TableColumn<Question, Integer> questionIdColumn;

    @FXML
    private TableColumn<Question, String> questionTextColumn;
    @FXML
    private TableColumn<Question, String> optionAColumn;
    @FXML
    private TableColumn<Question, String> optionBColumn;
    @FXML
    private TableColumn<Question, String> optionCColumn;
    @FXML
    private TableColumn<Question, String> optionDColumn;
    private final QuestionService questionService = new QuestionServiceImpl();
    @FXML
    public void initialize() {
        questionIdColumn.setCellValueFactory(new PropertyValueFactory<>("questionId"));
        questionTextColumn.setCellValueFactory(new PropertyValueFactory<>("questionText"));
        optionAColumn.setCellValueFactory(new PropertyValueFactory<>("optionA"));
        optionBColumn.setCellValueFactory(new PropertyValueFactory<>("optionB"));
        optionCColumn.setCellValueFactory(new PropertyValueFactory<>("optionC"));
        optionDColumn.setCellValueFactory(new PropertyValueFactory<>("optionD"));
        loadQuestionData();

         questionTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
             if (newSelection != null) {
                 //Get the selected question.
                 //Do something with it.
             }
         });
    }
    private void loadQuestionData() {
        ObservableList<Question> questions = FXCollections.observableArrayList(questionService.getAllQuestions());
        questionTableView.setItems(questions);
    }
}