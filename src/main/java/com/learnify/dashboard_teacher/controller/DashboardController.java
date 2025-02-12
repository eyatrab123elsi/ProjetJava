package com.learnify.dashboard_teacher.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import com.learnify.utilisateur.controllers.ProfilController;
import com.learnify.utilisateur.entities.Utilisateur;
import com.learnify.utilisateur.services.UtilisateurService;
import com.learnify.cours.service.CoursService;
import com.learnify.cours.service.CoursServiceImpl;
import com.learnify.dashboard_teacher.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardController {

    @FXML
    private BarChart<String, Number> userBarChart;

    @FXML
    private BarChart<String, Number> courseBarChart;

    @FXML
    private BarChart<String, Number> correctAnswersBarChart;

    @FXML
    private BarChart<String, Number> absenceBarChart;

    private UtilisateurService utilisateurService = new UtilisateurService();
    private CoursService coursService = new CoursServiceImpl();
    private String userEmail; // Store the user's email

    public void initialize() {
        setYAxisTo0To7(userBarChart);
        setYAxisTo0To7(courseBarChart);
        setYAxisTo0To7(correctAnswersBarChart);
        setYAxisTo0To7(absenceBarChart);

        updateUserBarChart();
        updateCourseBarChart();
        updateCorrectAnswersBarChart();
        updateAbsencesBarChart();
    }

    private void setYAxisTo0To7(BarChart<String, Number> barChart) {
        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(7);
        yAxis.setTickUnit(1);
        yAxis.setMinorTickVisible(false);
        yAxis.setTickLabelsVisible(true);
        yAxis.setAutoRanging(false);
    }

    private void updateUserBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Utilisateurs par Rôle");
        series.getData().add(new XYChart.Data<>("Etudiants", utilisateurService.getUtilisateursByRole("Etudiant").size()));
        series.getData().add(new XYChart.Data<>("Enseignants", utilisateurService.getUtilisateursByRole("Enseignant").size()));
        userBarChart.getData().add(series);
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #FF6347;");
        }
    }

    private void updateCourseBarChart() {
        XYChart.Series<String, Number> courseSeries = new XYChart.Series<>();
        courseSeries.setName("Nombre de cours par titre");
        coursService.getAllCours().forEach(cours ->
                courseSeries.getData().add(new XYChart.Data<>(cours.getTitre(), 1))
        );
        courseBarChart.getData().add(courseSeries);
        for (XYChart.Data<String, Number> data : courseSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #7B68EE;");
        }
    }

    private void updateCorrectAnswersBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Réponses correctes par utilisateur");
        String query = """
            SELECT u.nom, COUNT(r.result_id) AS correct_answers
            FROM result r
            JOIN utilisateurs u ON r.user_id = u.id
            JOIN question q ON r.quiz_id = q.quiz_id
            WHERE r.score >= q.correct_answer_index
            GROUP BY u.nom
        """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String userName = resultSet.getString("nom");
                int correctCount = resultSet.getInt("correct_answers");
                series.getData().add(new XYChart.Data<>(userName, correctCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        correctAnswersBarChart.getData().add(series);
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #32CD32;");
        }
    }

    private void updateAbsencesBarChart() {
        XYChart.Series<String, Number> absenceSeries = new XYChart.Series<>();
        absenceSeries.setName("Nombre d'Absences par Utilisateur");
        String query = """
            SELECT u.nom, COUNT(a.absence_id) AS absences
            FROM absence a
            JOIN utilisateurs u ON a.student_id = u.id
            GROUP BY u.nom
        """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String userName = resultSet.getString("nom");
                int absenceCount = resultSet.getInt("absences");
                absenceSeries.getData().add(new XYChart.Data<>(userName, absenceCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        absenceBarChart.getData().add(absenceSeries);
        for (XYChart.Data<String, Number> data : absenceSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #FFD700;");
        }
    }

    @FXML
    private void handleAbsenceIconClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/absenceInterface/ConsultationAbsenceView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestion des Absences");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleQuizIconClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/teacher_dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Quiz Teacher");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCourseIconClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/AddCourse.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Course Teacher");
            stage.setScene(new Scene(root));
            stage.setWidth(800);
            stage.setHeight(600);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRatingIconClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rating/CourseReviewView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Rating");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProfilIconClick() {
        try {
            UtilisateurService utilisateurService = new UtilisateurService();
            Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userEmail);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ProfilPage.fxml"));
            Parent root = loader.load();
            ProfilController profilController = loader.getController();
            profilController.setUtilisateur(utilisateur);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Profil");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleLogoutIconClick() {
        try {
            // Close the current dashboard window
            Stage currentStage = (Stage) userBarChart.getScene().getWindow();
            currentStage.close();

            // Load the authentication page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/Authentification.fxml"));
            Parent root = loader.load();
            Stage authStage = new Stage();
            authStage.setScene(new Scene(root));
            authStage.setTitle("Authentification");
            authStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
