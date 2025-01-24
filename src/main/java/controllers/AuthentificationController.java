package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;  // Ajoute cette ligne
import javafx.scene.control.Button;    // Ajoute cette ligne
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class AuthentificationController {

    @FXML
    private TextField usernameField;   // Déclare la variable TextField pour le champ nom d'utilisateur

    @FXML
    private TextField passwordField;   // Déclare la variable TextField pour le champ mot de passe

    @FXML
    private Button loginButton;        // Déclare la variable Button pour le bouton de connexion

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs.");
        } else {
            System.out.println("Nom d'utilisateur: " + username);
            System.out.println("Mot de passe: " + password);
        }
    }

    // Gestion du bouton d'inscription pour rediriger vers la page d'inscription
    @FXML
    private void handleRegisterButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inscription.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page d'Inscription");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
