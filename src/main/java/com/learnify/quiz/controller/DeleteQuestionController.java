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
import javafx.scene.control.TextArea;
import java.util.List;

public class DeleteQuestionController {
    @FXML private ComboBox<Quiz> quizComboBox;
    @FXML private ComboBox<Question> questionComboBox;
    @FXML private TextArea questionTextArea;
    
    private final QuestionService questionService = new QuestionServiceImpl();
    private final QuizService quizService = new QuizServiceImpl();

    @FXML
    public void initialize() {
        quizComboBox.setItems(FXCollections.observableArrayList(quizService.getAllQuizzes()));
        quizComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null) {
                loadQuestionsForQuiz(newVal.getQuizId());
            }
        });
        
        questionComboBox.setOnAction(this::handleQuestionSelection);
    }

    private void loadQuestionsForQuiz(int quizId) {
        List<Question> questions = questionService.getQuestionsByQuizId(quizId);
        questionComboBox.setItems(FXCollections.observableArrayList(questions));
    }

    private void handleQuestionSelection(ActionEvent event) {
        Question selected = questionComboBox.getValue();
        if(selected != null) {
            questionTextArea.setText(selected.getQuestionText());
        }
    }

    @FXML
    void handleDeleteQuestion(ActionEvent event) {
        Question selected = questionComboBox.getValue();
        if(selected != null) {
            questionService.deleteQuestion(selected.getQuestionId());
            questionComboBox.getItems().remove(selected);
            questionTextArea.clear();
        }
    }
}