package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.UtilisateurService;

public class ProfilController {

    @FXML
    private TextField nomField, emailField, passwordField;  // Ajoutez passwordField

    @FXML
    private Button modifierButton;

    @FXML
    private Text feedbackText;

    @FXML
    public void modifierProfil() {
        String nom = nomField.getText();
        String email = emailField.getText();
        String newPassword = passwordField.getText();  // Récupérer le mot de passe

        UtilisateurService utilisateurService = new UtilisateurService();
        boolean success = utilisateurService.mettreAJourProfil(nom, email, newPassword);  // Passer le mot de passe

        if (success) {
            feedbackText.setText("Profil mis à jour avec succès !");
        } else {
            feedbackText.setText("Erreur lors de la mise à jour.");
        }
    }
}
