package com.learnify.absence.view;

import com.learnify.absence.entities.AbsenceEntity;
import com.learnify.absence.service.AbsenceService;
import com.learnify.absence.service.AbsenceServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;

public class EditAbsenceController {

    @FXML
    private DatePicker absenceDatePicker;
    @FXML
    private TextField reasonField;

    private AbsenceEntity absence;
    private final AbsenceService absenceService = new AbsenceServiceImpl();

    public void setAbsence(AbsenceEntity absence) {
        this.absence = absence;
        absenceDatePicker.setValue(LocalDate.parse(absence.getAbsenceDate()));
        reasonField.setText(absence.getReason());
    }

    @FXML
    private void handleUpdateAbsence() {
        if (absence == null) {
            showAlert("Erreur", "Aucune absence sélectionnée pour modification.");
            return;
        }
        LocalDate newDate = absenceDatePicker.getValue();
        String newReason = reasonField.getText();

        if (newDate == null || newReason == null || newReason.trim().isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }
        absence.setAbsenceDate(newDate.toString());
        absence.setReason(newReason);

        absenceService.updateAbsence(absence);
        showAlert("Succès", "Absence modifiée avec succès !");
        Stage stage = (Stage) absenceDatePicker.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
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
