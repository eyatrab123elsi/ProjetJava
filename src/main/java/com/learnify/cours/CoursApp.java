package com.learnify.cours;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class CoursApp extends Application {

    private static String css; // Declare the CSS as static

    @Override
    public void start(Stage stage) {
        try {
            // Load the CSS file
            css = Objects.requireNonNull(getClass().getResource("/cours/style.css")).toExternalForm();

            // Load the FXML file
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cours/AddCourse.fxml")));
            Scene scene = new Scene(root);

            // Apply the CSS file to the scene
            applyCSS(scene);

            stage.setTitle("Gestion des Cours");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Static method to get the CSS path, for reuse in other scenes
    public static String getCss() {
        return css;
    }

    // Public method to apply the CSS to any scene
    public static void applyCSS(Scene scene) {
        scene.getStylesheets().add(css);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
