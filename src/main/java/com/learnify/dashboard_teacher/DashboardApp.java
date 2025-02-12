package com.learnify.dashboard_teacher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox; // Utilisation de VBox (ou autre conteneur selon ton fichier FXML)
import javafx.stage.Stage;

public class DashboardApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charge le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard_teacher/dashboard.fxml"));

            // Si ton fichier FXML utilise VBox comme conteneur principal
            VBox root = loader.load();

            // Crée la scène et l'ajoute à la fenêtre
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            // Titre de la fenêtre
            primaryStage.setTitle("Dashboard Teacher");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Affiche les erreurs si le fichier FXML n'est pas trouvé ou si un problème survient
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
