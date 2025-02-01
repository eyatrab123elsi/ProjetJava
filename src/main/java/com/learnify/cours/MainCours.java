package com.learnify.cours;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainCours extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/cours.fxml")));
        Scene scene = new Scene(root);


        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/style.css")).toExternalForm());

        primaryStage.setTitle("Gestion des Cours");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
