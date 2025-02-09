package com.learnify.quiz.controller;

import com.learnify.quiz.model.Question;
import com.learnify.quiz.model.Quiz;
import com.learnify.quiz.service.QuestionService;
import com.learnify.quiz.service.QuestionServiceImpl;
import com.learnify.quiz.service.QuizService;
import com.learnify.quiz.service.QuizServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.util.List;

public class EditQuestionController {

    @FXML private ComboBox<Quiz> quizComboBox;
    @FXML private ComboBox<Question> questionComboBox;
    @FXML private TextField questionTextField;
    @FXML private TextField optionATextField;
    @FXML private TextField optionBTextField;
    @FXML private TextField optionCTextField;
    @FXML private TextField optionDTextField;
    @FXML private ComboBox<String> correctAnswerComboBox;

    private final QuestionService questionService = new QuestionServiceImpl();
    private final QuizService quizService = new QuizServiceImpl();
    private Question selectedQuestion;

    @FXML
    public void initialize() {
        setupQuizSelection();
        initializeCorrectAnswerComboBox();
    }

    private void setupQuizSelection() {
        // Load all quizzes
        quizComboBox.setItems(FXCollections.observableArrayList(quizService.getAllQuizzes()));
        
        // Handle quiz selection changes
        quizComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadQuestionsForQuiz(newVal.getQuizId());
            }
        });
    }

    private void initializeCorrectAnswerComboBox() {
        correctAnswerComboBox.setItems(FXCollections.observableArrayList("A", "B", "C", "D"));
    }

    private void loadQuestionsForQuiz(int quizId) {
        List<Question> questions = questionService.getQuestionsByQuizId(quizId);
        ObservableList<Question> observableList = FXCollections.observableArrayList(questions);
        questionComboBox.setItems(observableList);
        
        // Clear previous selection
        questionComboBox.getSelectionModel().clearSelection();
        clearFormFields();
        
        // Set up question selection handler
        questionComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                populateFormFields(newVal);
            }
        });
    }

    private void populateFormFields(Question question) {
        selectedQuestion = question;
        questionTextField.setText(question.getQuestionText());
        optionATextField.setText(question.getOptionA());
        optionBTextField.setText(question.getOptionB());
        optionCTextField.setText(question.getOptionC());
        optionDTextField.setText(question.getOptionD());
        correctAnswerComboBox.getSelectionModel().select(question.getCorrectAnswerIndex());
    }

    @FXML
    void handleSaveQuestion(ActionEvent event) {
        if (selectedQuestion == null) {
            System.out.println("Please select a question first.");
            return;
        }

        if (!validateForm()) {
            System.out.println("All fields are required!");
            return;
        }

        updateQuestionFromForm();
        questionService.updateQuestion(selectedQuestion);
        System.out.println("Question updated successfully!");
        refreshQuestionList();
        clearFormFields();
    }

    private boolean validateForm() {
        return !questionTextField.getText().isEmpty() &&
               !optionATextField.getText().isEmpty() &&
               !optionBTextField.getText().isEmpty() &&
               !optionCTextField.getText().isEmpty() &&
               !optionDTextField.getText().isEmpty();
    }

    private void updateQuestionFromForm() {
        selectedQuestion.setQuestionText(questionTextField.getText());
        selectedQuestion.setOptionA(optionATextField.getText());
        selectedQuestion.setOptionB(optionBTextField.getText());
        selectedQuestion.setOptionC(optionCTextField.getText());
        selectedQuestion.setOptionD(optionDTextField.getText());
        selectedQuestion.setCorrectAnswerIndex(correctAnswerComboBox.getSelectionModel().getSelectedIndex());
    }

    private void refreshQuestionList() {
        Quiz selectedQuiz = quizComboBox.getValue();
        if (selectedQuiz != null) {
            loadQuestionsForQuiz(selectedQuiz.getQuizId());
        }
    }

    private void clearFormFields() {
        questionTextField.clear();
        optionATextField.clear();
        optionBTextField.clear();
        optionCTextField.clear();
        optionDTextField.clear();
        correctAnswerComboBox.getSelectionModel().clearSelection();
    }
}