package com.learnify.quiz.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.util.Objects;

public class StudentQuizController {
    @FXML
    private BorderPane mainPane;

    @FXML
    void handleQuizList() {
        loadUI("quiz_list");
    }
    @FXML
    void handleTakeQuiz() {
        loadUI("take_quiz");
    }
    @FXML
    void handleViewResults() {
       loadUI("quiz_results");
    }
    private void loadUI(String ui) {
        Parent root;
        try {
             root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/quiz/"+ui+".fxml")));
            mainPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}