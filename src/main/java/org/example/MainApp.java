package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.learnify.utilisateur.utils.DBConnection;

import java.sql.Connection;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Tester la connexion à la base de données
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                // Connexion réussie
                System.out.println("Connexion réussie à la base de données !");
            } else {
                // Connexion échouée
                showErrorMessage("Erreur de connexion", "Impossible de se connecter à la base de données.");
                return;  // Arrêter le chargement de l'interface si la connexion échoue
            }

            // Charger le fichier FXML pour l'écran d'authentification
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

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
