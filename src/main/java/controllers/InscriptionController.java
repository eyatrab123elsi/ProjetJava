package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.UtilisateurService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InscriptionController {

    @FXML
    private TextField nomField, emailField;

    @FXML
    private PasswordField motDePasseField;

    @FXML
    private Button inscrireButton;

    @FXML
    private Text feedbackText;

    // Expression régulière pour vérifier un format d'email valide
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @FXML
    public void inscrireUtilisateur() {
        String nom = nomField.getText();
        String email = emailField.getText();
        String motDePasse = motDePasseField.getText();

        if (nom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            feedbackText.setText("Veuillez remplir tous les champs.");
            return;
        }

        if (!isEmailValid(email)) {
            feedbackText.setText("L'email n'est pas valide.");
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

    // Méthode de validation de l'email
    private boolean isEmailValid(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
