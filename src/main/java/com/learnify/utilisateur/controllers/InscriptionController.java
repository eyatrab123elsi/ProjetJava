package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

        // Vérification des champs obligatoires
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || role == null || dateNaissance.isEmpty()) {
            showErrorAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Validation du nom
        if (!nom.matches("[a-zA-Z\\s]+")) {
            showErrorAlert("Erreur", "Le nom ne doit contenir que des lettres.");
            return;
        }

        // Validation du prénom
        if (!prenom.matches("[a-zA-Z\\s]+")) {
            showErrorAlert("Erreur", "Le prénom ne doit contenir que des lettres.");
            return;
        }

        // Validation de l'adresse
        if (!adresse.matches("[a-zA-Z0-9\\s]+")) {
            showErrorAlert("Erreur", "L'adresse ne doit contenir que des lettres et des chiffres.");
            return;
        }

        // Validation du mot de passe
        if (motDePasse.length() != 12) {
            showErrorAlert("Erreur", "Le mot de passe doit contenir exactement 12 caractères.");
            return;
        }

        // Validation du téléphone
        if (!telephone.matches("\\d+")) {
            showErrorAlert("Erreur", "Le numéro de téléphone ne doit contenir que des chiffres.");
            return;
        }

        // Ajout de l'utilisateur
        UtilisateurService utilisateurService = new UtilisateurService();
        boolean success = utilisateurService.ajouterUtilisateur(nom, prenom, email, motDePasse, telephone, adresse, dateNaissance, role);

        if (success) {
            feedbackText.setText("Inscription réussie ! En attente de validation.");
        } else {
            showErrorAlert("Erreur", "L'email existe déjà.");
        }
    }

    @FXML
    private void handleRetourButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
            StackPane root = loader.load(); // Utiliser StackPane ici
            Scene scene = new Scene(root);

            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page d'Accueil");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Erreur", "Impossible de charger la page d'accueil.");
        }
    }

    // Méthode pour afficher une alerte d'erreur
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

        // Ajouter des écouteurs pour valider les champs en temps réel
        nomField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s]*")) {
                nomField.setText(oldValue);
                showErrorAlert("Erreur", "Le nom ne doit contenir que des lettres.");
            }
        });

        prenomField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s]*")) {
                prenomField.setText(oldValue);
                showErrorAlert("Erreur", "Le prénom ne doit contenir que des lettres.");
            }
        });

        adresseField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9\\s]*")) {
                adresseField.setText(oldValue);
                showErrorAlert("Erreur", "L'adresse ne doit contenir que des lettres et des chiffres.");
            }
        });

        motDePasseField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 12) {
                motDePasseField.setText(oldValue);
                showErrorAlert("Erreur", "Le mot de passe doit contenir exactement 12 caractères.");
            }
        });

        telephoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                telephoneField.setText(oldValue);
                showErrorAlert("Erreur", "Le numéro de téléphone ne doit contenir que des chiffres.");
            }
        });
    }
}
