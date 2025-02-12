package com.learnify.cours;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class CoursApp extends Application {

    private static String css;

    @Override
    public void start(Stage stage) {
        try {
            // Load CSS
            css = Objects.requireNonNull(getClass().getResource("/cours/style.css")).toExternalForm();

            // Load FXML
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cours/RoleSelection.fxml")));
            
            // Create scene with initial dimensions
            Scene scene = new Scene(root, 800, 600);  // Set initial window size
            
            // Apply CSS
            applyCSS(scene);

            // Configure stage
            stage.setTitle("Gestion des Cours");
            stage.setScene(scene);
            
            // Window configuration
            stage.setFullScreen(false);      // Disable full screen
            stage.setMaximized(false);       // Prevent starting maximized
            stage.setResizable(true);        // Allow window resizing
            
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCss() {
        return css;
    }

    public static void applyCSS(Scene scene) {
        scene.getStylesheets().add(css);
    }

    public static void main(String[] args) {
        launch(args);
    }
}