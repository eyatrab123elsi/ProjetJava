package com.learnify.rating.controller;

import com.learnify.rating.model.CourseReview;
import com.learnify.rating.service.CourseReviewService;
import com.learnify.cours.service.CoursServiceImpl;
import com.learnify.cours.entities.Cours;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CourseReviewController {

    @FXML
    private ComboBox<Cours> courseComboBox;  // Modifier pour contenir des objets Cours
    @FXML
    private TextArea reviewTextArea;
    @FXML
    private Slider ratingSlider;
    @FXML
    private Label starsLabel;

    private CourseReviewService reviewService;
    private CoursServiceImpl coursService;

    @FXML
    public void initialize() {
        reviewService = new CourseReviewService();
        coursService = new CoursServiceImpl();

        // Récupérer les cours depuis la base de données
        List<Cours> courses = coursService.getAllCours();

        // Extraire les titres des cours et les ajouter au ComboBox, mais garder l'objet complet Cours
        ObservableList<Cours> courseList = FXCollections.observableArrayList(courses);

        // Ajouter les cours au ComboBox
        courseComboBox.setItems(courseList);

        // Afficher uniquement le titre dans le ComboBox
        courseComboBox.setCellFactory(param -> new javafx.scene.control.ListCell<Cours>() {
            @Override
            protected void updateItem(Cours item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getTitre());  // Afficher seulement le titre
            }
        });

        // Initialisation des étoiles en fonction de la valeur initiale du Slider
        updateStarsLabel();

        // Mettre à jour les étoiles chaque fois que la valeur du Slider change
        ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> updateStarsLabel());

        // Définir une valeur par défaut au slider (par exemple, 3 étoiles)
        ratingSlider.setValue(3);  // Ou vous pouvez définir la valeur initiale selon vos préférences
    }

    @FXML
    public void submitReview() {
        // Récupérer l'objet Cours sélectionné à partir du ComboBox
        Cours selectedCourse = courseComboBox.getValue();

        // Vérifier si un cours a été sélectionné
        if (selectedCourse != null) {
            String reviewText = reviewTextArea.getText();
            int rating = (int) ratingSlider.getValue();
            boolean isAnonymous = true; // Vous pouvez changer cette logique si vous voulez permettre aux utilisateurs de choisir

            // Création de l'avis sans la date
            CourseReview review = new CourseReview(
                    selectedCourse.getId(),  // Utilisation de l'ID du cours ici
                    rating,
                    reviewText,
                    isAnonymous
            );

            // Sauvegarde de l'avis
            reviewService.saveReview(review);

            // Rediriger vers la vue des avis après la soumission
            goToCourseReviews(selectedCourse);
        } else {
            System.out.println("Veuillez sélectionner un cours.");
        }
    }

    // Mise à jour du Label des étoiles en fonction de la valeur du Slider
    private void updateStarsLabel() {
        int rating = (int) ratingSlider.getValue();
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < rating; i++) {
            stars.append("★");  // Affiche une étoile pour chaque point de la note
        }
        for (int i = rating; i < 5; i++) {
            stars.append("☆");  // Affiche une étoile vide pour les points restants
        }
        starsLabel.setText(stars.toString());
    }

    // Méthode pour rediriger vers la page des avis du cours
    private void goToCourseReviews(Cours course) {
        try {
            // Charger la scène des avis
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rating/course_reviews.fxml"));
            Parent root = loader.load();

            // Passer l'objet Cours au contrôleur de la scène des avis
            CourseReviewsDisplayController controller = loader.getController();
            controller.loadCourseReviews(course);

            // Créer une nouvelle scène et la définir comme scène principale
            Scene scene = new Scene(root);
            Stage stage = (Stage) courseComboBox.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
