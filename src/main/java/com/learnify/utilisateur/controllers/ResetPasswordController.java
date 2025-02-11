package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import com.learnify.utilisateur.services.UtilisateurService;
import com.learnify.utilisateur.services.PasswordResetService;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class ResetPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private Text feedbackText;

    @FXML
    private Button resetPasswordButton, backButton;

    @FXML
    public void handleResetPasswordButtonAction() {
        String email = emailField.getText().trim();

        if (email.isEmpty()) {
            feedbackText.setText("Veuillez entrer votre email.");
            return;
        }

        UtilisateurService utilisateurService = new UtilisateurService();
        boolean emailExists = utilisateurService.isEmailRegistered(email);

        if (emailExists) {
            // Générer un code de réinitialisation
            String resetCode = generateResetCode();

            // Stocker le code dans la base de données
            utilisateurService.storeResetCode(email, resetCode);

            // Envoyer le code par email
            PasswordResetService passwordResetService = new PasswordResetService();
            passwordResetService.sendPasswordResetCode(email, resetCode);

            feedbackText.setText("Un code de réinitialisation a été envoyé à votre email.");

            // Rediriger vers l'écran de saisie du code
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/EnterResetCode.fxml"));
                Parent root = loader.load();

                EnterResetCodeController controller = loader.getController();
                controller.setEmail(email); // Passer l'email au contrôleur suivant

                Stage stage = (Stage) resetPasswordButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            feedbackText.setText("Aucun compte trouvé avec cet email.");
        }
    }

    // Générer un code de réinitialisation à 6 chiffres
    private String generateResetCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Code à 6 chiffres
        return String.valueOf(code);
    }

    @FXML
    public void handleBackButtonAction() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/utilisateur/Authentification.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}