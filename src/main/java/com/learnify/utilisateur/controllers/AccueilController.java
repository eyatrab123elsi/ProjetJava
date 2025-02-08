package com.learnify.utilisateur.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AccueilController {

    @FXML
    private ImageView imageView; // ImageView défini dans le fichier FXML

    @FXML
    private Button signUpButton; // Bouton Sign Up

    @FXML
    private Button signInButton; // Bouton Sign In

    private final List<Image> images = new ArrayList<>();
    private int currentIndex = 0;

    @FXML
    public void initialize() {
        // Chargement des images avec vérification des ressources
        loadImage("/image/image33.png");
        loadImage("/image/IA (1).png");
        loadImage("/image/image44.png");

        // Vérifier si les images sont bien chargées avant de démarrer l'animation
        if (!images.isEmpty()) {
            imageView.setImage(images.get(0));
            startImageAnimation();
        } else {
            System.err.println("Aucune image n'a été chargée.");
        }

        // Ajouter des effets interactifs aux boutons uniquement s'ils sont bien injectés
        if (signUpButton != null && signInButton != null) {
            addHoverEffect(signUpButton);
            addHoverEffect(signInButton);
        } else {
            System.err.println("Erreur : Les boutons ne sont pas injectés correctement.");
        }
    }

    /**
     * Charge une image et l'ajoute à la liste si elle existe.
     */
    private void loadImage(String path) {
        URL imageUrl = getClass().getResource(path);
        if (imageUrl != null) {
            images.add(new Image(imageUrl.toExternalForm()));
        } else {
            System.err.println("Erreur : Impossible de charger l'image " + path);
        }
    }

    /**
     * Démarre l'animation de changement d'image.
     */
    private void startImageAnimation() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> {
                    currentIndex = (currentIndex + 1) % images.size();
                    imageView.setImage(images.get(currentIndex));
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Gestion de l'ouverture de la fenêtre d'inscription.
     */
    @FXML
    public void handleInscription() {
        System.out.println("Bouton Sign Up cliqué");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inscription.fxml"));
            Parent root = loader.load();

            Platform.runLater(() -> {
                Stage stage = (Stage) signUpButton.getScene().getWindow();
                stage.setTitle("Inscription");
                stage.setScene(new Scene(root));
            });

        } catch (Exception e) {
            System.err.println("Erreur lors de l'ouverture de la fenêtre d'inscription : " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Gestion de l'ouverture de la fenêtre de connexion.
     */
    @FXML
    public void handleConnexion() {
        loadWindow("/Authentification.fxml", "Connexion");
    }

    /**
     * Charge une nouvelle fenêtre et remplace la scène actuelle.
     */
    private void loadWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Exécuter sur le thread JavaFX pour éviter les conflits d'interface
            Platform.runLater(() -> {
                Stage stage = (Stage) signUpButton.getScene().getWindow();
                stage.setTitle(title);
                stage.setScene(new Scene(root));
            });
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la fenêtre " + title + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ajoute un effet de survol et de clic à un bouton.
     */
    private void addHoverEffect(Button button) {
        DropShadow shadow = new DropShadow();

        button.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            button.setEffect(shadow);
            button.setScaleX(1.05);
            button.setScaleY(1.05);
        });

        button.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            button.setEffect(null);
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });

        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            button.setScaleX(0.95);
            button.setScaleY(0.95);
        });

        button.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            button.setScaleX(1.05);
            button.setScaleY(1.05);
        });
    }
}
