package com.learnify.absence.view;

import com.learnify.absence.entities.AbsenceEntity;
import com.learnify.absence.service.AbsenceService;
import com.learnify.absence.service.AbsenceServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class AbsenceManagementController {

    @FXML
    private TextField studentIdField;

    @FXML
    private TextField courseIdField;

    @FXML
    private DatePicker absenceDatePicker;

    @FXML
    private TextField reasonField;

    @FXML
    private Button addAbsenceButton;

    @FXML
    private TableView<AbsenceEntity> absenceTable;

    @FXML
    private TableColumn<AbsenceEntity, Number> idColumn;

    @FXML
    private TableColumn<AbsenceEntity, Number> studentIdColumn;

    @FXML
    private TableColumn<AbsenceEntity, Number> courseIdColumn;

    @FXML
    private TableColumn<AbsenceEntity, String> dateColumn;

    @FXML
    private TableColumn<AbsenceEntity, String> reasonColumn;

    // Instance du service pour interagir avec la base de données
    private final AbsenceService absenceService = new AbsenceServiceImpl();

    private final ObservableList<AbsenceEntity> absenceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set cell value factories for each column
        idColumn.setCellValueFactory(new PropertyValueFactory<>("absenceId"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("absenceDate"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));

        // Set preferred widths for each column
        idColumn.setPrefWidth(50); // Adjusted to 50
        studentIdColumn.setPrefWidth(80); // Adjusted to 80
        courseIdColumn.setPrefWidth(80); // Adjusted to 80
        dateColumn.setPrefWidth(120); // Adjusted to 120
        reasonColumn.setPrefWidth(200); // Adjusted to 200

        // Load data into the table
        loadAbsences();
    }

    // Action du bouton "Add Absence"
    @FXML
    private void handleAddAbsence() {
        try {
            int studentId = Integer.parseInt(studentIdField.getText());
            int courseId = Integer.parseInt(courseIdField.getText());
            LocalDate selectedDate = absenceDatePicker.getValue();
            if (selectedDate == null) {
                showAlert("Erreur", "Veuillez sélectionner une date d'absence.");
                return;
            }
            String absenceDate = selectedDate.toString();
            String reason = reasonField.getText();

            // Création de l'entité absence et enregistrement
            AbsenceEntity absence = new AbsenceEntity();
            absence.setStudentId(studentId);
            absence.setCourseId(courseId);
            absence.setAbsenceDate(absenceDate);
            absence.setReason(reason);

            absenceService.createAbsence(absence);
            showAlert("Succès", "Absence enregistrée avec succès!");

            // Effacer les champs
            studentIdField.clear();
            courseIdField.clear();
            absenceDatePicker.setValue(null);
            reasonField.clear();

            // Recharger le tableau
            loadAbsences();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Les champs Student ID et Course ID doivent être numériques.");
        }
    }

    // Méthode pour charger et afficher la liste des absences
    private void loadAbsences() {
        List<AbsenceEntity> absences = absenceService.getAllAbsences();
        absenceList.setAll(absences);
        absenceTable.setItems(absenceList);
    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
