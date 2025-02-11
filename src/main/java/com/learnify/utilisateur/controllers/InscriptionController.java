package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import com.learnify.utilisateur.services.UtilisateurService;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import javafx.scene.layout.StackPane;


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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/Accueil.fxml"));
            StackPane root = loader.load(); // Utiliser StackPane ici
            Scene scene = new Scene(root);

            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page d'Accueil");
        } catch (IOException e) {
            e.printStackTrace();
            // Afficher une alerte à l'utilisateur
            showErrorAlert("Erreur", "Impossible de charger la page d'accueil.");
        }
    }
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-background-color: #5E35B1; -fx-text-fill: white;");
        });
        button.setOnMouseExited(event -> {
            button.setStyle("-fx-background-color: #7E57C2; -fx-text-fill: white;");
        });
    }

    @FXML
    public void initialize() {
        addHoverEffect(inscrireButton);
        addHoverEffect(retourButton);

        // Initialiser le ComboBox des rôles
        ObservableList<String> roles = FXCollections.observableArrayList("Étudiant", "Enseignant");
        roleComboBox.setItems(roles);
    }
}