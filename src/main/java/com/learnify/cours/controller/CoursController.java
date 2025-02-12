package com.learnify.cours.controller;

import com.learnify.cours.entities.Cours;
import javafx.beans.property.SimpleStringProperty;
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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;

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
        colDuree.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDuree())));
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
        loadCoursesFromDatabase(); // Load courses when UI starts
    }

    private void loadCoursesFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/learnify";
        String username = "root";
        String password = "";

        String sql = "SELECT titre, description, duree, fichier FROM cours";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            coursList.clear();

            while (rs.next()) {
                String titre = rs.getString("titre");
                String description = rs.getString("description");
                int duree = rs.getInt("duree");
                String fichier = rs.getString("fichier");

                Cours cours = new Cours(titre, description, duree, fichier);
                coursList.add(cours);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger les cours depuis la base de données.", Alert.AlertType.ERROR);
        }
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
        String fichier = (fichierPDF != null) ? savePDFToLocal(fichierPDF) : "Aucun fichier";

        if (titre.isEmpty() || description.isEmpty() || duree.isEmpty()) {
            showAlert("Champs vides", "Veuillez remplir tous les champs.", Alert.AlertType.WARNING);
            return;
        }

        try {
            int dureeInt = Integer.parseInt(duree);
            Cours nouveauCours = new Cours(titre, description, dureeInt, fichier);
            saveCoursToDatabase(nouveauCours);
            loadCoursesFromDatabase(); // Refresh table
            showAlert("Succès", "Cours ajouté avec succès !", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Durée invalide. Veuillez entrer un nombre.", Alert.AlertType.ERROR);
        }

        titreField.clear();
        descriptionField.clear();
        dureeField.clear();
        fichierPDF = null;
    }

    @FXML
    public void supprimerCours(ActionEvent event) {
        Cours selectedCours = coursTable.getSelectionModel().getSelectedItem();

        if (selectedCours != null) {
            deleteCoursFromDatabase(selectedCours);
            coursList.remove(selectedCours);
            showAlert("Suppression", "Le cours a été supprimé.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Erreur", "Veuillez sélectionner un cours à supprimer.", Alert.AlertType.WARNING);
        }
    }

    private void deleteCoursFromDatabase(Cours cours) {
        String url = "jdbc:mysql://localhost:3306/learnify";
        String username = "root";
        String password = "";

        String sql = "DELETE FROM cours WHERE titre = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cours.getTitre());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de supprimer le cours.", Alert.AlertType.ERROR);
        }
    }

    private void saveCoursToDatabase(Cours cours) {
        String url = "jdbc:mysql://localhost:3306/learnify";
        String username = "root";
        String password = "";

        String sql = "INSERT INTO cours (titre, description, duree, fichier) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cours.getTitre());
            stmt.setString(2, cours.getDescription());
            stmt.setInt(3, cours.getDuree());
            stmt.setString(4, cours.getPdfPath());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ajouter le cours à la base de données.", Alert.AlertType.ERROR);
        }
    }

    private String savePDFToLocal(File pdfFile) {
        if (pdfFile == null) return "Aucun fichier";

        File destDir = new File("src/main/resources/cours/pdfs");

        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        File destFile = new File(destDir, pdfFile.getName());

        try {
            Files.copy(pdfFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "Aucun fichier";
        }
    }

    private void showAlert(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
