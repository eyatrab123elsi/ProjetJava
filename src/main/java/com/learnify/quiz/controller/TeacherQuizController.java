package com.learnify.quiz.controller;

import com.learnify.quiz.QuizApp; // Import Main to use its method
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class TeacherQuizController {

    @FXML
    private BorderPane mainPane;


    @FXML
    void handleCreateQuestion() {
       loadUI("/quiz/create_question.fxml");
    }

    @FXML
    void handleEditQuestion() {
         loadUI("/quiz/edit_question.fxml");
    }

    @FXML
    void handleDeleteQuestion() {
         loadUI("/quiz/delete_question.fxml");
    }

    @FXML
    void handleQuizList() {
        loadUI("/quiz/quiz_list.fxml");
    }

     private void loadUI(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            // Apply CSS to the root (which is the parent of the new scene content)
            if (root != null) {
               Scene scene = mainPane.getScene();
                  //Main.applyCSS(scene); //Apply the CSS
                 mainPane.setCenter(root);
             }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}