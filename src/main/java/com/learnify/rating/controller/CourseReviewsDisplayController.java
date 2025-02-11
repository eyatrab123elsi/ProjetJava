package com.learnify.rating.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import com.learnify.rating.model.CourseReview;
import com.learnify.cours.entities.Cours;
import com.learnify.rating.service.CourseReviewService;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CourseReviewsDisplayController {

    @FXML
    private ListView<HBox> reviewsListView;
    @FXML
    private TextArea courseDetailsTextArea;
    @FXML
    private Label averageRatingLabel; // Ajouter le Label pour afficher la moyenne

    private final CourseReviewService reviewService = new CourseReviewService();

    // Méthode pour afficher la moyenne avec des étoiles
    private String formatRatingWithStars(double rating) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < Math.round(rating); i++) {
            stars.append("★ "); // Ajouter une étoile pour chaque point de la note
        }
        return stars.toString().trim(); // Retourner la chaîne de caractères d'étoiles
    }

    public void loadCourseReviews(Cours course) {
        // Afficher les détails du cours
        courseDetailsTextArea.setText("Course: " + course.getTitre() + "\nDescription: " + course.getDescription());

        // Récupérer les avis pour le cours
        List<CourseReview> reviews = reviewService.getReviewsForCourse(course.getId());

        // Calculer la moyenne des évaluations
        double averageRating = calculateAverageRating(reviews);

        // Afficher la moyenne dans le Label
        if (reviews.isEmpty()) {
            averageRatingLabel.setText("No reviews yet.");
        } else {
            averageRatingLabel.setText("Average Rating: " + formatRatingWithStars(averageRating));
            averageRatingLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");
        }

        // Vider la ListView
        reviewsListView.getItems().clear();

        // Ajouter chaque avis dans la ListView
        for (CourseReview review : reviews) {
            // Créer une HBox contenant l'avis et les boutons
            HBox hbox = new HBox(10);
            hbox.setStyle("-fx-padding: 5px; -fx-alignment: center-left;");

            // TextArea pour afficher l'avis
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < review.getRating(); i++) {
                stars.append("★ "); // Ajouter une étoile pour chaque point de la note
            }

            TextArea reviewTextArea = new TextArea("Review: " + review.getMessage() + "\nRating: " + stars.toString().trim());
            reviewTextArea.setWrapText(true);
            reviewTextArea.setEditable(false);
            HBox.setHgrow(reviewTextArea, Priority.ALWAYS);

            // Bouton "Modify"
            Button modifyButton = new Button("Modify");
            modifyButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            modifyButton.setOnAction(event -> modifyReview(review, reviewTextArea));

            // Bouton "Delete"
            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
            deleteButton.setOnAction(event -> deleteReview(review, hbox));

            // Ajouter les éléments à la HBox
            hbox.getChildren().addAll(reviewTextArea, modifyButton, deleteButton);

            // Ajouter l'avis dans la ListView
            reviewsListView.getItems().add(hbox);
        }
    }

    private double calculateAverageRating(List<CourseReview> reviews) {
        if (reviews.isEmpty()) {
            return 0.0; // Si aucun avis, retourner 0
        }

        double totalRating = 0.0;
        for (CourseReview review : reviews) {
            totalRating += review.getRating(); // Ajouter chaque note à totalRating
        }

        // Calculer la moyenne
        return totalRating / reviews.size();
    }

    @FXML
    private void modifyReview(CourseReview review, TextArea reviewTextArea) {
        TextInputDialog dialog = new TextInputDialog(review.getMessage());
        dialog.setTitle("Modify Review");
        dialog.setHeaderText("Modify the review for this course");
        dialog.setContentText("Enter the new review message:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty()) {
            String updatedMessage = result.get();

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to modify this review?");
            Optional<ButtonType> confirmResult = confirmAlert.showAndWait();

            if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
                review.setMessage(updatedMessage);
                if (reviewService.updateReview(review)) {
                    reviewTextArea.setText("Review: " + updatedMessage + "\nRating: " + review.getRating() + " ★");

                    // Recalculer et afficher la moyenne des avis après la modification
                    List<CourseReview> updatedReviews = reviewService.getReviewsForCourse(review.getCourseId());
                    double newAverageRating = calculateAverageRating(updatedReviews);
                    averageRatingLabel.setText("Average Rating: " + formatRatingWithStars(newAverageRating));
                } else {
                    showErrorAlert("Failed to update the review.");
                }
            }
        }
    }

    @FXML
    private void deleteReview(CourseReview review, HBox hbox) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this review?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (reviewService.deleteReview(review.getId())) {
                reviewsListView.getItems().remove(hbox);  // Supprime l'avis de la ListView

                // Recalculer et afficher la moyenne des avis après la suppression
                List<CourseReview> updatedReviews = reviewService.getReviewsForCourse(review.getCourseId());
                double newAverageRating = calculateAverageRating(updatedReviews);
                averageRatingLabel.setText("Average Rating: " + formatRatingWithStars(newAverageRating));
            } else {
                showErrorAlert("Failed to delete the review.");
            }
        }
    }

    private void showErrorAlert(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
    @FXML
    private Button backButton;
    @FXML
    private void goBackToRatingPage() {
        try {
            // Charger la scène ou la page de notation du cours
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rating/CourseReviewView.fxml")); // Remplacez par le bon chemin
            Parent root = loader.load();

            // Créer une nouvelle scène avec la nouvelle vue
            Scene scene = new Scene(root);

            // Obtenir la fenêtre (stage) actuelle et la mettre à jour avec la nouvelle scène
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show(); // Afficher la nouvelle scène
        } catch (IOException e) {
            e.printStackTrace(); // Afficher une erreur si le chargement échoue
        }
    }


}
