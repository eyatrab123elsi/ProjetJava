package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import com.learnify.utilisateur.services.UtilisateurService;
import javafx.stage.Stage;

import java.io.IOException;

public class EnterResetCodeController {

    @FXML
    private TextField codeField;

    @FXML
    private Text feedbackText;

    private String email; // L'email sera passé depuis le contrôleur précédent

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    public void handleValidateCodeButtonAction() {
        String code = codeField.getText().trim();
        if (code.isEmpty()) {
            feedbackText.setText("Veuillez entrer le code.");
            return;
        }

        UtilisateurService utilisateurService = new UtilisateurService();
        boolean isValid = utilisateurService.validateResetCode(email, code);

        if (isValid) {
            feedbackText.setText("Code valide. Vous pouvez maintenant réinitialiser votre mot de passe.");

            // Rediriger vers la page de réinitialisation du mot de passe
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ResetPasswordPage.fxml"));
                AnchorPane root = loader.load();

                ResetPasswordPageController controller = loader.getController();
                controller.setEmail(email); // Passer l'email au contrôleur suivant

                Stage stage = (Stage) codeField.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            feedbackText.setText("Code invalide ou expiré.");
        }
    }

    // Méthode pour gérer le clic sur l'icône de retour

    @FXML
    private void handleBackToAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml")); // Chemin corrigé
            StackPane root = loader.load(); // Charger en tant que StackPane
            Scene scene = new Scene(root);

            Stage stage = (Stage) codeField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Accueil");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}