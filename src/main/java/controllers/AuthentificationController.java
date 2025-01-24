package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AuthentificationController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Logique de connexion (vérification des champs, affichage d'un message, etc.)
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs.");
        } else {
            System.out.println("Nom d'utilisateur: " + username);
            System.out.println("Mot de passe: " + password);
            // Ajouter ici la logique d'authentification si nécessaire
        }
    }
}
