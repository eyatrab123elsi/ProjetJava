package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.learnify.utilisateur.entities.Utilisateur;

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

    private Utilisateur utilisateur;

    // Cette méthode sera appelée pour passer l'utilisateur au contrôleur
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        afficherProfil();
    }

    // Méthode pour afficher les informations de l'utilisateur dans les labels
    private void afficherProfil() {
        if (utilisateur != null) {
            nomLabel.setText(utilisateur.getNom());
            prenomLabel.setText(utilisateur.getPrenom());
            emailLabel.setText(utilisateur.getEmail());
            telephoneLabel.setText(utilisateur.getTelephone());
            adresseLabel.setText(utilisateur.getAdresse());
            roleLabel.setText(utilisateur.getRole());

            // Utilisation de la méthode getFormattedDateNaissance()
            dateNaissanceLabel.setText(utilisateur.getFormattedDateNaissance());
        }
    }
}
