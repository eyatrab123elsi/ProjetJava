package com.learnify.dashboard.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import com.learnify.utilisateur.services.UtilisateurService;
import com.learnify.cours.service.CoursService;
import com.learnify.cours.service.CoursServiceImpl;
import com.learnify.dashboard.DatabaseConnection;
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
    private BarChart<String, Number> absenceBarChart; // Nouveau graphique pour les absences

    private UtilisateurService utilisateurService = new UtilisateurService();
    private CoursService coursService = new CoursServiceImpl();

    public void initialize() {
        // Modifier les axes Y pour les graphiques
        setYAxisTo0To7(userBarChart);
        setYAxisTo0To7(courseBarChart);
        setYAxisTo0To7(correctAnswersBarChart);
        setYAxisTo0To7(absenceBarChart);

        updateUserBarChart();
        updateCourseBarChart();
        updateCorrectAnswersBarChart();
        updateAbsencesBarChart(); // Appel de la méthode pour afficher les absences
    }

    private void setYAxisTo0To7(BarChart<String, Number> barChart) {
        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        yAxis.setLowerBound(0);  // Définir la borne inférieure à 0
        yAxis.setUpperBound(7);  // Définir la borne supérieure à 7
        yAxis.setTickUnit(1);    // Définir l'unité de graduation à 1 pour des nombres entiers naturels
        yAxis.setMinorTickVisible(false); // Désactiver les ticks mineurs
        yAxis.setTickLabelsVisible(true); // Afficher les labels des ticks
        yAxis.setAutoRanging(false); // Désactiver l'auto-échelle
    }

    private void updateUserBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Utilisateurs par Rôle");

        // Ajouter les données pour les étudiants et enseignants
        series.getData().add(new XYChart.Data<>("Etudiants", utilisateurService.getUtilisateursByRole("Etudiant").size()));
        series.getData().add(new XYChart.Data<>("Enseignants", utilisateurService.getUtilisateursByRole("Enseignant").size()));

        userBarChart.getData().add(series);

        // Appliquer un style à chaque barre
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

        // Appliquer un style à chaque barre
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

        // Appliquer un style à chaque barre
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

        // Appliquer un style à chaque barre
        for (XYChart.Data<String, Number> data : absenceSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #FFD700;");
        }
    }
}
