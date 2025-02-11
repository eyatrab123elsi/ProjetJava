package com.learnify.utilisateur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.learnify.utilisateur.utils.DBConnection;

import java.sql.Connection;

public class UtilisateurApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Tester la connexion à la base de données
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                showErrorMessage("Erreur de connexion", "Impossible de se connecter à la base de données.");
                return;
            }

            // Tester le chargement de l'image
            try {
                Image testImage = new Image(getClass().getResource("/utilisateur/image/e22.png").toExternalForm());
                if (testImage.isError()) {
                    System.out.println("Erreur lors du chargement de l'image !");
                    showErrorMessage("Erreur d'image", "Impossible de charger l'image : /image/e22.png");
                    return;
                } else {
                    System.out.println("Image chargée avec succès !");
                }
            } catch (Exception e) {
                System.out.println("Exception lors du chargement de l'image : " + e.getMessage());
                e.printStackTrace();
                showErrorMessage("Erreur critique", "Une exception s'est produite lors du chargement de l'image.");
                return;
            }

            // Charger l'interface d'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/Accueil.fxml"));
            StackPane root = loader.load(); // Utiliser StackPane comme racine pour correspondre au FXML

            // Définir la scène principale
            Scene scene = new Scene(root, 1200, 800); // Définir la taille de la fenêtre
            primaryStage.setScene(scene);
            primaryStage.setTitle("Accueil - Learning Platform");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
