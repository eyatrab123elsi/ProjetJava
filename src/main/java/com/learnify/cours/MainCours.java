package com.learnify.cours;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainCours extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {

            URL fxmlLocation = getClass().getClassLoader().getResource("cours/AddCourse.fxml");
            if (fxmlLocation == null) {
                throw new IllegalArgumentException("FXML file not found: AddCourse.fxml");
            }
            Parent root = FXMLLoader.load(fxmlLocation);


            Scene scene = new Scene(root);


            URL cssLocation = getClass().getClassLoader().getResource("cours/style.css");
            if (cssLocation != null) {
                scene.getStylesheets().add(cssLocation.toExternalForm());
            } else {
                System.err.println("⚠ Warning: CSS file not found");
            }


            primaryStage.setTitle("Gestion des Cours");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
