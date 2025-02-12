package com.learnify.absence.view;

import com.learnify.absence.entities.AbsenceEntity;
import com.learnify.absence.service.AbsenceService;
import com.learnify.absence.service.AbsenceServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardAbsenceController {

    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String, Number> barChart;

    private final AbsenceService absenceService = new AbsenceServiceImpl();

    @FXML
    public void initialize() {
        List<AbsenceEntity> absences = absenceService.getAllAbsences();

        Map<String, Integer> courseCount = new HashMap<>();
        for (AbsenceEntity absence : absences) {
            String course = absence.getCourseName();
            courseCount.put(course, courseCount.getOrDefault(course, 0) + 1);
        }
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : courseCount.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        pieChart.setData(pieData);

        Map<String, Integer> monthCount = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (AbsenceEntity absence : absences) {
            LocalDate date = LocalDate.parse(absence.getAbsenceDate(), formatter);
            String month = date.getMonth().toString();
            monthCount.put(month, monthCount.getOrDefault(month, 0) + 1);
        }
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Absences par Mois");
        for (Map.Entry<String, Integer> entry : monthCount.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        barChart.getData().add(series);
    }

    @FXML
    private void handleRetour() {
        Stage stage = (Stage) pieChart.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
