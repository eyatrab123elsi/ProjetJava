package com.learnify.utilisateur.controllers;

import com.learnify.utilisateur.services.UtilisateurService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ResetPasswordPageController {

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Text feedbackText;

    private String email; // L'email sera passé depuis le contrôleur précédent

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    private void handleSubmitButtonAction() {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            feedbackText.setText("Veuillez remplir tous les champs.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            feedbackText.setText("Les mots de passe ne correspondent pas.");
            return;
        }

        // Réinitialiser le mot de passe
        UtilisateurService utilisateurService = new UtilisateurService();
        boolean success = utilisateurService.resetPassword(email, newPassword);

        if (success) {
            feedbackText.setText("Mot de passe réinitialisé avec succès !");
            feedbackText.setFill(javafx.scene.paint.Color.GREEN);
        } else {
            feedbackText.setText("Erreur lors de la réinitialisation du mot de passe.");
        }
    }


    @FXML
    private void handleBackToAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml")); // Chemin corrigé
            StackPane root = loader.load(); // Charger en tant que StackPane
            Scene scene = new Scene(root);

            Stage stage = (Stage) newPasswordField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Accueil");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}