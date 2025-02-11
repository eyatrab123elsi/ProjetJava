package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.learnify.utilisateur.entities.Utilisateur;
import com.learnify.utilisateur.services.UtilisateurService;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.time.LocalDate;

public class ProfilController {

    @FXML
    private Label nomLabel;

    @FXML
    private Label prenomLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label telephoneLabel;

    @FXML
    private Label adresseLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label dateNaissanceLabel;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField roleField;

    @FXML
    private TextField adresseField;

    @FXML
    private DatePicker dateNaissancePicker;  // Déclaration du DatePicker
    @FXML private Button logoutButton;
    private Utilisateur utilisateur;
    private UtilisateurService utilisateurService;

    public ProfilController() {
        utilisateurService = new UtilisateurService();  // Assurez-vous d'instancier le service
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        afficherProfil();
        System.out.println("Utilisateur mis à jour : " + utilisateur);
    }

    @FXML
    private void initialize() {
        // Appliquer un TextFormatter pour n'accepter que des chiffres
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter());
        telephoneField.setTextFormatter(formatter);

        // Connexion du bouton de déconnexion
        logoutButton.setOnAction(event -> handleLogout());
    }

    private void afficherProfil() {
        if (utilisateur != null) {
            nomField.setText(utilisateur.getNom());
            prenomField.setText(utilisateur.getPrenom());
            emailField.setText(utilisateur.getEmail());
            telephoneField.setText(utilisateur.getTelephone());
            adresseField.setText(utilisateur.getAdresse());
            roleField.setText(utilisateur.getRole());
            // Mettre à jour le DatePicker avec la date de naissance
            if (utilisateur.getDateNaissance() != null) {
                dateNaissancePicker.setValue(utilisateur.getDateNaissance());
            }
        }
    }

    @FXML
    private void updateProfil() {
        // Récupérer les nouvelles valeurs des champs de texte
        String nouveauNom = nomField.getText();
        String nouveauPrenom = prenomField.getText();
        String nouveauEmail = emailField.getText(); // Nouvel email
        String nouveauTelephone = telephoneField.getText();
        String nouvelleAdresse = adresseField.getText();
        String nouvellerole = roleField.getText();
        LocalDate nouvelleDateNaissance = dateNaissancePicker.getValue();

        // Récupérer l'ancien email
        String ancienEmail = utilisateur.getEmail();

        // Mettre à jour l'utilisateur
        utilisateur.setNom(nouveauNom);
        utilisateur.setPrenom(nouveauPrenom);
        utilisateur.setEmail(nouveauEmail); // Mettre à jour l'email dans l'objet utilisateur
        utilisateur.setTelephone(nouveauTelephone);
        utilisateur.setAdresse(nouvelleAdresse);
        utilisateur.setRole(nouvellerole);
        utilisateur.setDateNaissance(nouvelleDateNaissance);

        // Appeler le service pour mettre à jour l'utilisateur dans la base de données
        boolean miseAJourReussie = utilisateurService.Modify(utilisateur, ancienEmail);

        // Afficher une alerte en fonction du résultat
        if (miseAJourReussie) {
            // Alerte de succès
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Mise à jour réussie");
            alert.setHeaderText(null);
            alert.setContentText("Votre profil a été mis à jour avec succès !");
            alert.showAndWait();
        } else {
            // Alerte d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de mise à jour");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la mise à jour de votre profil.");
            alert.showAndWait();
        }

        // Recharger les informations du profil
        afficherProfil();
    }
    @FXML
    private void handleLogout() {
        System.out.println("Déconnexion réussie !");

        // Fermer la fenêtre actuelle
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();

        // Ouvrir la page d'accueil
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/utilisateur/Authentification.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
