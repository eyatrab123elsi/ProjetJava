package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le f ichier FXML pour l'écran d'authentification
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Authentification.fxml"));

            AnchorPane root = loader.load(); // Charge le FXML

            // Définir la scène principale
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Application Learning Platform");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
