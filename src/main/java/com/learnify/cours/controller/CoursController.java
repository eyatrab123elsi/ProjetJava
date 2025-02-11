package com.learnify.cours.controller;

import com.learnify.cours.entities.Cours;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import java.io.File;
import java.awt.Desktop;
import java.io.IOException;

public class CoursController {

    @FXML
    private TextField titreField, dureeField, descriptionField;
    @FXML
    private TableView<Cours> coursTable;
    @FXML
    private TableColumn<Cours, String> colTitre, colDescription, colDuree;
    @FXML
    private TableColumn<Cours, String> colFichier;

    private File fichierPDF;
    private final ObservableList<Cours> coursList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDuree.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getDuree())));

        colFichier.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPdfPath()));
        colFichier.setCellFactory(column -> new TableCell<>() {
            private final Hyperlink link = new Hyperlink();

            {
                link.setOnAction(event -> {
                    Cours cours = getTableView().getItems().get(getIndex());
                    ouvrirFichierPDF(cours.getPdfPath());
                });
            }

            @Override
            protected void updateItem(String pdfPath, boolean empty) {
                super.updateItem(pdfPath, empty);
                if (empty || pdfPath == null || pdfPath.isEmpty() || pdfPath.equals("Aucun fichier")) {
                    setGraphic(null);
                } else {
                    link.setText(new File(pdfPath).getName());
                    setGraphic(link);
                }
            }
        });

        coursTable.setItems(coursList);
    }

    @FXML
    public void choisirFichierPDF(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fichierPDF = fileChooser.showOpenDialog(null);
    }

    private void ouvrirFichierPDF(String pdfPath) {
        if (pdfPath != null && !pdfPath.isEmpty()) {
            File file = new File(pdfPath);
            if (file.exists()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    showAlert("Erreur", "Impossible d'ouvrir le fichier.", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Attention", "Le fichier PDF n'existe plus.", Alert.AlertType.WARNING);
            }
        }
    }

    @FXML
    public void ajouterCours(ActionEvent event) {
        String titre = titreField.getText().trim();
        String description = descriptionField.getText().trim();
        String duree = dureeField.getText().trim();
        String fichier = (fichierPDF != null) ? fichierPDF.getAbsolutePath() : "Aucun fichier";

        if (titre.isEmpty() || description.isEmpty() || duree.isEmpty()) {
            showAlert("Champs vides", "Veuillez remplir tous les champs.", Alert.AlertType.WARNING);
            return;
        }

        int dureeInt = Integer.parseInt(duree);
        Cours nouveauCours = new Cours(titre, description, dureeInt, fichier);
        coursList.add(nouveauCours);

        titreField.clear();
        descriptionField.clear();
        dureeField.clear();
        fichierPDF = null;

        showAlert("Succès", "Cours ajouté avec succès !", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void supprimerCours(ActionEvent event) {
        Cours selectedCours = coursTable.getSelectionModel().getSelectedItem();

        if (selectedCours != null) {
            coursList.remove(selectedCours);
            showAlert("Suppression", "Le cours a été supprimé.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Erreur", "Veuillez sélectionner un cours à supprimer.", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void ouvrirFichierPDF(ActionEvent actionEvent) {
    }
}