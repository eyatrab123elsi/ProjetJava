package com.learnify.absence.view;

import com.learnify.absence.entities.AbsenceEntity;
import com.learnify.absence.service.AbsenceService;
import com.learnify.absence.service.AbsenceServiceImpl;
import com.learnify.cours.entities.Cours;
import com.learnify.cours.service.CoursService;
import com.learnify.cours.service.CoursServiceImpl;
import com.learnify.utilisateur.entities.Utilisateur;
import com.learnify.utilisateur.services.UtilisateurService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class AjoutAbsenceController {
    @FXML
    private ComboBox<String> classeComboBox;
    @FXML
    private ComboBox<Utilisateur> studentComboBox;
    @FXML
    private ComboBox<Cours> coursComboBox;
    @FXML
    private DatePicker absenceDatePicker;
    @FXML
    private TextField reasonField;


    private final AbsenceService absenceService = new AbsenceServiceImpl();
    private final UtilisateurService utilisateurService = new UtilisateurService();
    private final CoursService coursService = new CoursServiceImpl();

    @FXML
    public void initialize() {
        // Charger la liste des classes
        List<String> classes = utilisateurService.getDistinctClasses();
        classeComboBox.getItems().setAll(classes);

        // charger les étudiants correspondants
        classeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                List<Utilisateur> students = utilisateurService.getStudentsByClass(newVal);
                studentComboBox.getItems().setAll(students);
            }
        });

        // Charger la liste des cours
        List<Cours> coursList = coursService.getAllCours();
        coursComboBox.getItems().setAll(coursList);


        coursComboBox.setCellFactory(lv -> new ListCell<Cours>() {
            @Override
            protected void updateItem(Cours item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTitre());
            }
        });
        coursComboBox.setButtonCell(new ListCell<Cours>() {
            @Override
            protected void updateItem(Cours item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTitre());
            }
        });
    }

    @FXML
    private void handleAddAbsence() {
        Utilisateur selectedStudent = studentComboBox.getValue();
        if (selectedStudent == null) {
            showAlert("Erreur", "Veuillez sélectionner un étudiant.");
            return;
        }
        int studentId = selectedStudent.getId();

        Cours selectedCours = coursComboBox.getValue();
        if (selectedCours == null) {
            showAlert("Erreur", "Veuillez sélectionner un cours.");
            return;
        }
        int courseId = (int) selectedCours.getId();

        LocalDate selectedDate = absenceDatePicker.getValue();
        if (selectedDate == null) {
            showAlert("Erreur", "Veuillez sélectionner une date d'absence.");
            return;
        }
        String absenceDate = selectedDate.toString();

        String reason = reasonField.getText();

        AbsenceEntity absence = new AbsenceEntity();
        absence.setStudentId(studentId);
        absence.setCourseId(courseId);
        absence.setAbsenceDate(absenceDate);
        absence.setReason(reason);

        absenceService.createAbsence(absence);
        System.out.println("Insertion absence - Student ID: " + absence.getStudentId() +
                ", Course ID: " + absence.getCourseId());

        showAlert("Succès", "Absence enregistrée avec succès !");


        Stage stage = (Stage) absenceDatePicker.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAnnuler() {
        Stage stage = (Stage) absenceDatePicker.getScene().getWindow();
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
