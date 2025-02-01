package com.learnify.cours.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.learnify.cours.entities.Cours;
import com.learnify.cours.service.CoursService;
import com.learnify.cours.service.CoursServiceImpl;

public class CoursController {

    private final CoursService coursService = new CoursServiceImpl();
    private final ObservableList<Cours> coursList = FXCollections.observableArrayList();

    @FXML
    private TextField titreField, descriptionField, dureeField;

    @FXML
    private TableView<Cours> coursTable;

    @FXML
    private TableColumn<Cours, Long> colId;

    @FXML
    private TableColumn<Cours, String> colTitre, colDescription;

    @FXML
    private TableColumn<Cours, Integer> colDuree;

    @FXML
    public void initialize() {

        coursTable.setItems(coursList);
        afficherCours();
    }

    @FXML
    private void ajouterCours() {
        String titre = titreField.getText();
        String description = descriptionField.getText();
        int duree;

        try {
            duree = Integer.parseInt(dureeField.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Durée invalide, veuillez entrer un nombre entier !");
            return;
        }

        Cours newCours = new Cours(null, titre, description, duree);
        coursService.addCours(newCours);
        coursList.add(newCours);


        titreField.clear();
        descriptionField.clear();
        dureeField.clear();
    }

    @FXML
    private void afficherCours() {
        coursList.setAll(coursService.getAllCours());
    }

    @FXML
    private void supprimerCours() {
        Cours selectedCours = coursTable.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            coursService.deleteCours(selectedCours.getId());
            coursList.remove(selectedCours);
        } else {
            showAlert("Aucun cours sélectionné", "Veuillez sélectionner un cours à supprimer.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
