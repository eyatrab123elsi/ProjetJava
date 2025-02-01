package com.learnify.quiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private static String css; // Declare the CSS as static

    @Override
    public void start(Stage stage) throws IOException {
        try {
             // Load the CSS file
             css = Objects.requireNonNull(getClass().getResource("/com/learnify/quiz/ui/styles.css")).toExternalForm();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/learnify/quiz/ui/login.fxml")));
            Scene scene = new Scene(root);

           // Apply the CSS file to the scene
           applyCSS(scene);

            stage.setTitle("Learnify");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Static method to get the CSS path, to reuse the stylesheet in others scenes
    public static String getCss() {
        return css;
    }
    
    // Public method to apply the CSS to the scene
    public static void applyCSS(Scene scene) {
      scene.getStylesheets().add(css);
    }
    
    public static void main(String[] args) {
        launch();
    }
}