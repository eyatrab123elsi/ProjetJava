package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import com.learnify.utilisateur.entities.Utilisateur;
import com.learnify.utilisateur.services.UtilisateurService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.function.UnaryOperator;

public class ProfilController {

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
    private DatePicker dateNaissancePicker;

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
        // Appliquer un TextFormatter pour n'accepter que des chiffres dans le champ téléphone
        UnaryOperator<TextFormatter.Change> filterTelephone = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Seuls les chiffres sont autorisés
                return change;
            }
            return null;
        };
        telephoneField.setTextFormatter(new TextFormatter<>(filterTelephone));

        // Appliquer un TextFormatter pour n'accepter que des lettres et des espaces dans le champ nom
        UnaryOperator<TextFormatter.Change> filterNom = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z\\s]*")) { // Seules les lettres et les espaces sont autorisés
                return change;
            }
            return null;
        };
        nomField.setTextFormatter(new TextFormatter<>(filterNom));

        // Appliquer un TextFormatter pour n'accepter que des lettres et des espaces dans le champ prénom
        UnaryOperator<TextFormatter.Change> filterPrenom = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z\\s]*")) { // Seules les lettres et les espaces sont autorisés
                return change;
            }
            return null;
        };
        prenomField.setTextFormatter(new TextFormatter<>(filterPrenom));

        // Appliquer un TextFormatter pour n'accepter que du texte dans le champ adresse
        UnaryOperator<TextFormatter.Change> filterAdresse = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z\\s,.-]*")) { // Lettres, espaces, virgules, points et tirets autorisés
                return change;
            }
            return null;
        };
        adresseField.setTextFormatter(new TextFormatter<>(filterAdresse));
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

        // Vérifier si l'email existe déjà
        if (utilisateurService.emailExiste(nouveauEmail) && !nouveauEmail.equals(utilisateur.getEmail())) {
            showErrorAlert("Erreur", "Cet email existe déjà. Veuillez en choisir un autre.");
            return;
        }

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

    // Méthode pour afficher une alerte d'erreur
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
