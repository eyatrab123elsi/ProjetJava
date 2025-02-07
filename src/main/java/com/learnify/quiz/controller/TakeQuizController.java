package com.learnify.quiz.controller;

import com.learnify.quiz.model.Question;
import com.learnify.quiz.model.Quiz;
import com.learnify.quiz.model.Result;
import com.learnify.quiz.service.QuestionService;
import com.learnify.quiz.service.QuestionServiceImpl;
import com.learnify.quiz.service.QuizService;
import com.learnify.quiz.service.QuizServiceImpl;
import com.learnify.quiz.service.ResultService;
import com.learnify.quiz.service.ResultServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TakeQuizController {
    @FXML
    private ComboBox<Quiz> quizComboBox;
    @FXML
    private Label questionLabel;
    @FXML
    private RadioButton optionARadio;
    @FXML
    private RadioButton optionBRadio;
    @FXML
    private RadioButton optionCRadio;
    @FXML
    private RadioButton optionDRadio;
    @FXML
    private VBox optionsVBox;
    private final QuestionService questionService = new QuestionServiceImpl();
     private final QuizService quizService = new QuizServiceImpl();
    private final ResultService resultService = new ResultServiceImpl();
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int userId = 2; //hardcoded for now
    @FXML
    public void initialize() {
        loadQuizData();
    }
    private void loadQuizData() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        ObservableList<Quiz> observableList = FXCollections.observableArrayList(quizzes);
         quizComboBox.setItems(observableList);
    }
    private void loadQuestionData(Quiz selectedQuiz) {
        System.out.println("loadQuestionData called.");
        // Load questions here using your question service.
       if(selectedQuiz != null){
           questions = questionService.getAllQuestions()
                    .stream()
                    .filter(question -> question.getQuizId() == selectedQuiz.getQuizId())
                   .collect(Collectors.toList());
           if (questions != null && !questions.isEmpty()){
               showQuestion(questions.get(currentQuestionIndex));
           }else{
               questionLabel.setText("No question found in this quiz.");
           }
       }
    }
    private void showQuestion(Question question) {
        questionLabel.setText(question.getQuestionText());
        optionARadio.setText(question.getOptionA());
        optionBRadio.setText(question.getOptionB());
        optionCRadio.setText(question.getOptionC());
        optionDRadio.setText(question.getOptionD());
        optionARadio.setSelected(false);
        optionBRadio.setSelected(false);
        optionCRadio.setSelected(false);
        optionDRadio.setSelected(false);
    }
    @FXML
    void handleSubmitQuiz(ActionEvent event) throws IOException {
         RadioButton selectedRadioButton = (RadioButton) optionsVBox.getChildren().stream()
            .filter(node -> node instanceof RadioButton)
           .map(node -> (RadioButton) node)
           .filter(RadioButton::isSelected)
            .findFirst()
            .orElse(null);
        if (selectedRadioButton == null) {
            System.out.println("You have to choose an option");
           return;
        }
        String userAnswer = selectedRadioButton.getText();
       int userAnswerIndex = getIndex(userAnswer);
         System.out.println("Selected User Answer: "+ userAnswer + " Index: "+ userAnswerIndex+ " Correct Answer Index:" + questions.get(currentQuestionIndex).getCorrectAnswerIndex() + " Current Index " + currentQuestionIndex);
       if (questions != null && !questions.isEmpty()){
           if (questions.get(currentQuestionIndex).getCorrectAnswerIndex() == userAnswerIndex){
              score++;
            }
       }
        if (currentQuestionIndex < questions.size() - 1) {
             System.out.println("cureent question ++");
            currentQuestionIndex++;
            showQuestion(questions.get(currentQuestionIndex));
         }else{
             System.out.println("Quiz submitted");
            questionLabel.setText("Quiz completed!");
            saveResult();
            loadResultUI();
         }
    }
     private int getIndex(String userAnswer){
        if (userAnswer.equals(optionARadio.getText())){ return 0;}
        if (userAnswer.equals(optionBRadio.getText())){ return 1;}
        if (userAnswer.equals(optionCRadio.getText())){ return 2;}
        if (userAnswer.equals(optionDRadio.getText())){ return 3;}
       return -1;
    }
     private void saveResult() {
         Quiz selectedQuiz = quizComboBox.getValue();
        Result result = new Result();
        result.setQuizId(selectedQuiz.getQuizId());
        result.setUserId(userId);
        result.setScore(score);
        resultService.createResult(result);
        System.out.println("Result saved.");
     }
      private void loadResultUI() throws IOException {
          Stage stage = (Stage) questionLabel.getScene().getWindow();
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/quiz_results.fxml"));
             Parent root = loader.load();
           ResultController controller = loader.getController();
         controller.setResult("Your score is "+ score + "/" + questions.size());
          Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
      }
      @FXML
      void handleQuizSelection(ActionEvent event) {
            System.out.println("handleQuizSelection called.");
            Quiz selectedQuiz =  quizComboBox.getValue();
           if (selectedQuiz != null){
                loadQuestionData(selectedQuiz);
             }
         }
}