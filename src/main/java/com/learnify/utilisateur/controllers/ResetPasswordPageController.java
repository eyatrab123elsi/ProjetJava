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

        // Valider le mot de passe
        if (!isPasswordValid(newPassword)) {
            feedbackText.setText("Le mot de passe doit contenir au moins 12 caractères, " +
                    "une majuscule, une minuscule, un chiffre et un caractère spécial.");
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

    private boolean isPasswordValid(String password) {
        // Vérifier la longueur du mot de passe
        if (password.length() < 12) {
            return false;
        }

        // Vérifier la présence d'au moins une majuscule
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Vérifier la présence d'au moins une minuscule
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Vérifier la présence d'au moins un chiffre
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Vérifier la présence d'au moins un caractère spécial
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return false;
        }

        return true;
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
