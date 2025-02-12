package com.learnify.utilisateur.controllers;

import com.learnify.dashboard_teacher.controller.DashboardController;
import com.learnify.dashboard_student.controller.DashboardStudentController;
import com.learnify.utilisateur.entities.Utilisateur;
import com.learnify.utilisateur.services.UtilisateurService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class AuthentificationController {

    // Champs de saisie
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField visiblePasswordField;

    @FXML
    private ToggleButton showPasswordToggle;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private ImageView backButton;

    // Images pour l'icône d'œil
    private final Image eyeOpenImage;
    private final Image eyeClosedImage;

    // Constructeur pour charger les images
    public AuthentificationController() {
        this.eyeOpenImage = loadImage("/utilisateur/image/eye_open.png");
        this.eyeClosedImage = loadImage("/utilisateur/image/eye_closed.png");
    }

    // Méthode pour charger une image de manière sécurisée
    private Image loadImage(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Image not found: " + path);
            }
            return new Image(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + path, e);
        }
    }

    // Initialisation du contrôleur
    @FXML
    public void initialize() {
        // Masquer le TextField visiblePasswordField par défaut
        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);

        // Gérer l'action du ToggleButton pour afficher/masquer le mot de passe
        showPasswordToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // Afficher le mot de passe en texte clair
                visiblePasswordField.setText(passwordField.getText());
                visiblePasswordField.setVisible(true);
                visiblePasswordField.setManaged(true);
                passwordField.setVisible(false);
                passwordField.setManaged(false);

                // Changer l'icône pour l'œil ouvert
                ((ImageView) showPasswordToggle.getGraphic()).setImage(eyeOpenImage);
            } else {
                // Masquer le mot de passe
                passwordField.setText(visiblePasswordField.getText());
                passwordField.setVisible(true);
                passwordField.setManaged(true);
                visiblePasswordField.setVisible(false);
                visiblePasswordField.setManaged(false);

                // Changer l'icône pour l'œil fermé
                ((ImageView) showPasswordToggle.getGraphic()).setImage(eyeClosedImage);
            }
        });
    }

    // Méthode pour gérer la connexion
    @FXML
    private void handleLoginButtonAction() {
        String email = usernameField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        UtilisateurService utilisateurService = new UtilisateurService();
        String role = utilisateurService.authentifierUtilisateur(email, password);

        if (role == null) {
            showAlert("Erreur", "Email ou mot de passe incorrect.");
        } else if (role.equals("NonValidé")) {
            showAlert("Erreur", "Votre compte n'a pas encore été validé par l'administrateur.");
        } else {
            showAlert("Succès", "Connexion réussie en tant que " + role + " !");
            if (role.equals("Admin")) {
                redirectToAdminPage();
            } else if (role.equals("Étudiant")) {
                redirectToStudentPage();
            } else {
                redirectToTeacherPage();
            }
        }
    }

    // Redirection vers la page d'administration
    private void redirectToAdminPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/AdminPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page d'Administration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToStudentPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard_student/dashboard.fxml"));
            Parent root = loader.load();
            DashboardStudentController controller = loader.getController();
            controller.setUserEmail(usernameField.getText()); // Pass the user's email
    
            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void redirectToTeacherPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard_teacher/dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.setUserEmail(usernameField.getText()); // Pass the user's email
    
            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    // Redirection vers la page de l'utilisateur (étudiant/enseignant)
    private void redirectToUserPage(String role) {
        try {
            UtilisateurService utilisateurService = new UtilisateurService();
            Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(usernameField.getText());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ProfilPage.fxml"));
            Parent root = loader.load();
            ProfilController profilController = loader.getController();

            profilController.setUtilisateur(utilisateur);

            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Profil de " + role);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Affichage d'une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Redirection vers la page d'inscription
    @FXML
    private void handleRegisterButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/Inscription.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Page d'Inscription");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Redirection vers la page de réinitialisation du mot de passe
    @FXML
    private void handleForgotPasswordButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/PasswordReset.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            if (forgotPasswordLink != null) {
                Stage stage = (Stage) forgotPasswordLink.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Demande de réinitialisation du mot de passe");
            } else {
                System.out.println("forgotPasswordLink is null!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Redirection vers la page d'accueil
    @FXML
    private void handleBackToAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/Accueil.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Accueil");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}