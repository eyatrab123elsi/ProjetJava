package com.learnify.absence.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.learnify.absence.entities.AbsenceEntity;
import com.learnify.absence.service.AbsenceService;
import com.learnify.absence.service.AbsenceServiceImpl;
import com.learnify.utilisateur.services.UtilisateurService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

public class ConsultationAbsenceController {

    @FXML
    private ComboBox<String> classeComboBox;
    @FXML
    private DatePicker dateDebutPicker;
    @FXML
    private DatePicker dateFinPicker;
    @FXML
    private TableView<AbsenceEntity> absenceTable;
    @FXML
    private TableColumn<AbsenceEntity, Number> idColumn;
    @FXML
    private TableColumn<AbsenceEntity, String> studentNameColumn;
    @FXML
    private TableColumn<AbsenceEntity, String> studentClasseColumn;
    @FXML
    private TableColumn<AbsenceEntity, String> courseNameColumn;
    @FXML
    private TableColumn<AbsenceEntity, String> dateColumn;
    @FXML
    private TableColumn<AbsenceEntity, String> reasonColumn;
    @FXML
    private TableColumn<AbsenceEntity, Void> actionColumn;

    // Services
    private final AbsenceService absenceService = new AbsenceServiceImpl();
    private final UtilisateurService utilisateurService = new UtilisateurService();

    private final ObservableList<AbsenceEntity> absenceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("absenceId"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentClasseColumn.setCellValueFactory(new PropertyValueFactory<>("studentClasse"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("absenceDate"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        absenceTable.setItems(absenceList);

        actionColumn.setCellFactory(col -> new TableCell<AbsenceEntity, Void>() {
            private final Button btnEdit = new Button();
            private final Button btnDelete = new Button();

            {

                ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/edit.png")));
                editIcon.setFitHeight(16);
                editIcon.setFitWidth(16);
                btnEdit.setGraphic(editIcon);
                btnEdit.setStyle("-fx-background-color: transparent;");

                // Charger l'icône de suppression depuis les ressources
                ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/delete.png")));
                deleteIcon.setFitHeight(16);
                deleteIcon.setFitWidth(16);
                btnDelete.setGraphic(deleteIcon);
                btnDelete.setStyle("-fx-background-color: transparent;");

                // Action du bouton d'édition
                btnEdit.setOnAction(e -> {
                    AbsenceEntity absence = getTableView().getItems().get(getIndex());
                    handleEditAbsence(absence);
                });
                // Action du bouton de suppression
                btnDelete.setOnAction(e -> {
                    AbsenceEntity absence = getTableView().getItems().get(getIndex());
                    handleDeleteAbsence(absence);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox(10, btnEdit, btnDelete);
                    setGraphic(container);
                }
            }
        });


        List<String> classes = utilisateurService.getDistinctClasses();
        classeComboBox.getItems().setAll(classes);


        loadAbsences();
    }

    @FXML
    private void handleFilterAbsences() {
        String selectedClasse = classeComboBox.getValue();
        if (selectedClasse == null) {
            showAlert("Erreur", "Veuillez sélectionner une classe pour filtrer.");
            return;
        }
        LocalDate startDate = dateDebutPicker.getValue();
        LocalDate endDate = dateFinPicker.getValue();
        if (startDate == null || endDate == null) {
            showAlert("Erreur", "Veuillez sélectionner une date de début et une date de fin pour filtrer.");
            return;
        }
        List<AbsenceEntity> filteredAbsences = absenceService.getAbsencesByFilters(selectedClasse, startDate, endDate);
        absenceList.setAll(filteredAbsences);
    }

    @FXML
    private void handleResetFilters() {
        classeComboBox.getSelectionModel().clearSelection();
        dateDebutPicker.setValue(null);
        dateFinPicker.setValue(null);
        loadAbsences();
    }

    @FXML
    private void openAjoutAbsenceView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/absenceInterface/AjoutAbsenceView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter une Absence");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            // Rafraîchir le tableau après fermeture de la fenêtre d'ajout
            loadAbsences();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExportExcel() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Absences");


        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID Absence");
        headerRow.createCell(1).setCellValue("Étudiant");
        headerRow.createCell(2).setCellValue("Classe");
        headerRow.createCell(3).setCellValue("Cours");
        headerRow.createCell(4).setCellValue("Date");
        headerRow.createCell(5).setCellValue("Raison");


        int rowNum = 1;
        for (AbsenceEntity absence : absenceList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(absence.getAbsenceId());
            row.createCell(1).setCellValue(absence.getStudentName());
            row.createCell(2).setCellValue(absence.getStudentClasse());
            row.createCell(3).setCellValue(absence.getCourseName());
            row.createCell(4).setCellValue(absence.getAbsenceDate());
            row.createCell(5).setCellValue(absence.getReason());
        }


        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        // Utilisation d'un FileChooser pour enregistrer le fichier Excel
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier Excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Excel", "*.xlsx"));
        Stage stage = (Stage) absenceTable.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                workbook.close();
                showAlert("Succès", "Fichier Excel exporté avec succès !");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors de l'exportation du fichier Excel !");
            }
        }
    }

    private void loadAbsences() {
        List<AbsenceEntity> absences = absenceService.getAllAbsences();
        System.out.println("Nombre d'absences chargées : " + absences.size());
        absenceList.setAll(absences);
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleDeleteAbsence(AbsenceEntity absence) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText("Confirmer la suppression");
        confirmAlert.setContentText("Voulez-vous vraiment supprimer cette absence ?");
        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            absenceService.deleteAbsence(absence.getAbsenceId());
            loadAbsences();
        }
    }

    private void handleEditAbsence(AbsenceEntity absence) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/absenceInterface/EditAbsenceView.fxml"));
            Parent root = loader.load();
            EditAbsenceController editController = loader.getController();
            editController.setAbsence(absence);
            Stage stage = new Stage();
            stage.setTitle("Modifier une Absence");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            loadAbsences();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openDashboardView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/absenceInterface/DashboardAbsenceView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Dashboard des Absences");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir le dashboard.");
        }
    }

}
