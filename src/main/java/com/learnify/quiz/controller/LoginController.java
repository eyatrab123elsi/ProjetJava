package com.learnify.quiz.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private ToggleGroup userType;

    @FXML
    private RadioButton teacherRadio;

    @FXML
    private RadioButton studentRadio;

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
        RadioButton selectedRadioButton = (RadioButton) userType.getSelectedToggle();
        if (selectedRadioButton != null) {
            String selectedType = selectedRadioButton.getText();
            Stage stage = (Stage) teacherRadio.getScene().getWindow(); // Get the Stage from teacherRadio
            Parent root;
            if (selectedType.equals("Teacher")) {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/learnify/quiz/ui/teacher_dashboard.fxml")));
            } else {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/learnify/quiz/ui/student_dashboard.fxml")));
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}