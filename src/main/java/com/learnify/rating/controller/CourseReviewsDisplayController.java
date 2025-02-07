package com.learnify.rating.controller;

import com.learnify.rating.model.CourseReview;
import com.learnify.cours.entities.Cours;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.List;

public class CourseReviewsDisplayController {

    @FXML
    private ListView<String> reviewsListView;  // Liste des avis
    @FXML
    private TextArea courseDetailsTextArea;    // Détails du cours

    // Méthode pour charger et afficher les avis du cours
    public void loadCourseReviews(Cours course) {
        // Afficher les détails du cours
        courseDetailsTextArea.setText("Course: " + course.getTitre() + "\nDescription: " + course.getDescription());

        // Récupérer les avis pour ce cours (vous pouvez adapter cette méthode selon votre service)
        List<CourseReview> reviews = getCourseReviews(course);

        // Afficher les avis dans le ListView
        for (CourseReview review : reviews) {
            reviewsListView.getItems().add("Rating: " + review.getRating() + " Stars\nReview: " + review.getMessage());
        }
    }

    // Méthode fictive pour récupérer des avis pour le cours (ajustez cette logique)
    private List<CourseReview> getCourseReviews(Cours course) {
        // Vous devez récupérer les avis pour ce cours via un service ou une base de données
        return List.of(
                new CourseReview(course.getId(), 5, "Excellent course!", true),
                new CourseReview(course.getId(), 4, "Good course, but could be more interactive.", false)
        );
    }
}
