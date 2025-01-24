package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.UtilisateurService;

public class InscriptionController {

    @FXML
    private TextField nomField, emailField;

    @FXML
    private PasswordField motDePasseField;

    @FXML
    private Button inscrireButton;

    @FXML
    private Text feedbackText;

    @FXML
    public void inscrireUtilisateur() {
        String nom = nomField.getText();
        String email = emailField.getText();
        String motDePasse = motDePasseField.getText();

        if (nom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            feedbackText.setText("Veuillez remplir tous les champs.");
            return;
        }

        UtilisateurService utilisateurService = new UtilisateurService();
        boolean success = utilisateurService.ajouterUtilisateur(nom, email, motDePasse, "Etudiant");

        if (success) {
            feedbackText.setText("Inscription réussie !");
        } else {
            feedbackText.setText("Erreur : L'email existe déjà.");
        }
    }
}
