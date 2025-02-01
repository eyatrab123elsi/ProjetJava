package com.learnify.rating.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.learnify.cours.entities.Cours;
import com.learnify.cours.service.CoursService;

import java.util.List;

public class ReviewController {

    @FXML
    private ComboBox<Cours> courseDropdown;

    @FXML
    private Label star1, star2, star3, star4, star5;

    @FXML
    private TextArea commentField;

    private int rating = 0;
    private final Label[] stars;

    public ReviewController() {
        stars = new Label[5]; // Initialisation du tableau
    }

    @FXML
    public void initialize() {
        CoursService coursService = new CoursService() {
            @Override
            public Cours getCoursById(Long id) {
                return null;
            }

            @Override
            public List<Cours> getAllCours() {
                return List.of();
            }

            @Override
            public void addCours(Cours cours) {

            }

            @Override
            public void deleteCours(Long id) {

            }
        };


        // Charger les cours disponibles
        List<Cours> courses = coursService.getAllCours();
        if (courses != null && !courses.isEmpty()) {
            courseDropdown.getItems().addAll(courses);
        } else {
            System.out.println("Aucun cours disponible !");
        }

        initializeStars();
    }

    /**
     * Configuration des étoiles avec gestion des clics
     */
    private void initializeStars() {
        Label[] starArray = {star1, star2, star3, star4, star5};

        for (int i = 0; i < starArray.length; i++) {
            stars[i] = starArray[i]; // Remplir le tableau stars
            stars[i].setText("☆");
            stars[i].setStyle("-fx-cursor: hand; -fx-font-size: 30;");

            int starIndex = i + 1; // Index de l'étoile sélectionnée
            stars[i].setOnMouseClicked(event -> updateStars(starIndex));
        }
    }

    /**
     * Met à jour la note et affiche les étoiles correctement
     */
    private void updateStars(int selectedRating) {
        rating = selectedRating;
        for (int i = 0; i < stars.length; i++) {
            stars[i].setText(i < rating ? "★" : "☆");
            stars[i].setStyle(i < rating ? "-fx-text-fill: gold;" : "-fx-text-fill: gray;");
        }
    }

    /**
     * Gestion de l'action du bouton Envoyer
     */
    @FXML
    private void handleSubmitAction() {
        Cours selectedCourse = courseDropdown.getValue();
        String comment = commentField.getText().trim();

        if (selectedCourse == null) {
            showAlert("Erreur", "Veuillez sélectionner un cours.");
            return;
        }

        if (rating == 0) {
            showAlert("Erreur", "Veuillez donner une note.");
            return;
        }

        if (comment.isEmpty()) {
            showAlert("Erreur", "Veuillez ajouter un commentaire.");
            return;
        }

        System.out.println("Cours : " + selectedCourse.getTitre() +
                ", Note : " + rating + " étoiles, Commentaire : " + comment);

        // TODO: Enregistrer l'avis en base de données
        showAlert("Succès", "Avis soumis avec succès !");
    }

    /**
     * Affiche une alerte d'information
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
