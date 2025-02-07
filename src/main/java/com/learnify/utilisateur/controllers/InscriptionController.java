package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import com.learnify.utilisateur.services.UtilisateurService;
import javafx.stage.Stage;
import java.io.IOException;

public class InscriptionController {

    @FXML
    private TextField nomField, prenomField, emailField, telephoneField, adresseField;

    @FXML
    private PasswordField motDePasseField;

    @FXML
    private DatePicker dateNaissancePicker;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Button inscrireButton, retourButton;

    @FXML
    private Text feedbackText;

    @FXML
    public void inscrireUtilisateur() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String telephone = telephoneField.getText();
        String adresse = adresseField.getText();
        String motDePasse = motDePasseField.getText();
        String dateNaissance = (dateNaissancePicker.getValue() != null) ? dateNaissancePicker.getValue().toString() : "";
        String role = roleComboBox.getValue();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || role == null || dateNaissance.isEmpty()) {
            feedbackText.setText("Veuillez remplir tous les champs obligatoires.");
            return;
        }

        UtilisateurService utilisateurService = new UtilisateurService();
        boolean success = utilisateurService.ajouterUtilisateur(nom, prenom, email, motDePasse, telephone, adresse, dateNaissance, role);

        if (success) {
            feedbackText.setText("Inscription réussie ! En attente de validation.");
        } else {
            feedbackText.setText("Erreur : L'email existe déjà.");
        }
    }

    @FXML
    private void handleRetourButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/Authentification.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page d'Authentification");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
