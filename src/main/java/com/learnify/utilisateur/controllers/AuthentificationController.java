package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.learnify.utilisateur.services.UtilisateurService;

import java.io.IOException;

public class AuthentificationController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button forgotPasswordButton;

    // Remplacez ou complétez la méthode existante handleLoginButtonAction par ce code

    @FXML
    private void handleLoginButtonAction() {
        String email = usernameField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        UtilisateurService utilisateurService = new UtilisateurService();
        String role = utilisateurService.authentifierUtilisateur(email, password);

        if (role == null) {
            showAlert("Erreur", "Email ou mot de passe incorrect.");
        } else if (role.equals("NonValidé")) {
            showAlert("Erreur", "Votre compte n'a pas encore été validé par l'administrateur.");
        } else {
            showAlert("Succès", "Connexion réussie en tant que " + role + " !");
            if (role.equals("Admin")) {
                redirectToAdminPage();
            } else {
                redirectToUserPage(role);
            }
        }
    }

    private void redirectToAdminPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page d'Administration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToUserPage(String role) {
        try {
            String fxmlFile = "/UserPage.fxml"; // Remplacez par le fichier FXML approprié
            if (role.equals("Étudiant")) {
                fxmlFile = "/StudentPage.fxml";
            } else if (role.equals("Enseignant")) {
                fxmlFile = "/TeacherPage.fxml";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page de " + role);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleRegisterButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inscription.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page d'Inscription");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleForgotPasswordButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PasswordReset.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) forgotPasswordButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Réinitialisation du mot de passe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}