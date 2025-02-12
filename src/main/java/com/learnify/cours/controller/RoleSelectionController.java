package com.learnify.cours.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class RoleSelectionController {

    @FXML
    private Button studentButton, teacherButton;


    @FXML
    public void enterAsStudent() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/ViewCouses.fxml"));
            Parent studentView = loader.load();

            // Set the scene with the student view
            Stage stage = (Stage) studentButton.getScene().getWindow();
            stage.setScene(new Scene(studentView));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to handle "Enter as Teacher" button click
    @FXML
    public void enterAsTeacher() {
        try {
            // Load the teacher view (the page where the add course functionality is available)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/AddCourse.fxml"));
            Parent teacherView = loader.load();

            // Set the scene with the teacher view
            Stage stage = (Stage) teacherButton.getScene().getWindow();
            stage.setScene(new Scene(teacherView));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
