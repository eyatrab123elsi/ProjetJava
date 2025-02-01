package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import com.learnify.utilisateur.services.UtilisateurService;
import javafx.stage.Stage;

import java.io.IOException;

public class ResetPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private Text feedbackText;

    @FXML
    private Button resetPasswordButton, backButton;

    @FXML
    public void handleResetPasswordButtonAction() {
        String email = emailField.getText();

        if (email.isEmpty()) {
            feedbackText.setText("Please enter your email.");
            return;
        }

        // Création d'une instance de UtilisateurService pour vérifier l'email
        UtilisateurService utilisateurService = new UtilisateurService();
        boolean emailExists = utilisateurService.isEmailRegistered(email);

        if (emailExists) {
            // Logique pour envoyer un lien de réinitialisation du mot de passe
            feedbackText.setText("A password reset link has been sent to your email.");
            // Ajoutez ici la logique pour envoyer l'email de réinitialisation
        } else {
            feedbackText.setText("No account found with this email.");
        }
    }

    @FXML
    public void handleBackButtonAction() {
        try {
            // Retour à l'écran de connexion
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Authentification.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
