package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.learnify.utilisateur.entities.Utilisateur;
import com.learnify.utilisateur.services.UtilisateurService;
import javafx.beans.property.SimpleStringProperty; // Importation ajoutée

import java.util.List;
import java.util.Optional;

public class AdminPage {

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableView<Utilisateur> teachersTable;
    @FXML
    private TableColumn<Utilisateur, String> nameColumn;
    @FXML
    private TableColumn<Utilisateur, String> emailColumn;
    @FXML
    private TableColumn<Utilisateur, Void> editColumn;
    @FXML
    private TableColumn<Utilisateur, Void> deleteColumn;

    @FXML
    private TableView<Utilisateur> studentsTable;
    @FXML
    private TableColumn<Utilisateur, String> nameColumnStudent;
    @FXML
    private TableColumn<Utilisateur, String> emailColumnStudent;
    @FXML
    private TableColumn<Utilisateur, Void> editColumnStudent;
    @FXML
    private TableColumn<Utilisateur, Void> deleteColumnStudent;

    @FXML
    private TableView<Utilisateur> pendingValidationTable;
    @FXML
    private TableColumn<Utilisateur, String> nameColumnPending;
    @FXML
    private TableColumn<Utilisateur, String> emailColumnPending;
    @FXML
    private TableColumn<Utilisateur, String> roleColumnPending;
    @FXML
    private TableColumn<Utilisateur, Void> validateColumn;
    @FXML
    private TableColumn<Utilisateur, Void> rejectColumn;

    @FXML
    private ComboBox<String> filterRoleComboBox;

    private ObservableList<Utilisateur> teachersData = FXCollections.observableArrayList();
    private ObservableList<Utilisateur> studentsData = FXCollections.observableArrayList();
    private ObservableList<Utilisateur> pendingValidationData = FXCollections.observableArrayList();
    private UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    public void initialize() {
        welcomeLabel.setText("Bienvenue sur la page d'administration !");

        // Configuration de la table des enseignants
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        // Ajout du bouton "Editer" pour les enseignants
        editColumn.setCellFactory(param -> new TableCell<Utilisateur, Void>() {
            private final Button editButton = new Button("Editer");

            {
                editButton.setOnAction(event -> handleEditTeacher(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(editButton);
                } else {
                    setGraphic(null);
                }
            }
        });

        // Ajout du bouton "Supprimer" pour les enseignants
        deleteColumn.setCellFactory(param -> new TableCell<Utilisateur, Void>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> handleDeleteTeacher(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(deleteButton);
                } else {
                    setGraphic(null);
                }
            }
        });

        // Configuration de la table des étudiants
        nameColumnStudent.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        emailColumnStudent.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        // Ajout du bouton "Editer" pour les étudiants
        editColumnStudent.setCellFactory(param -> new TableCell<Utilisateur, Void>() {
            private final Button editButton = new Button("Editer");

            {
                editButton.setOnAction(event -> handleEditStudent(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(editButton);
                } else {
                    setGraphic(null);
                }
            }
        });

        // Ajout du bouton "Supprimer" pour les étudiants
        deleteColumnStudent.setCellFactory(param -> new TableCell<Utilisateur, Void>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> handleDeleteStudent(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(deleteButton);
                } else {
                    setGraphic(null);
                }
            }
        });

        // Configuration de la table des utilisateurs en attente de validation
        nameColumnPending.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        emailColumnPending.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        roleColumnPending.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));

        // Ajout du bouton "Valider"
        validateColumn.setCellFactory(param -> new TableCell<Utilisateur, Void>() {
            private final Button validateButton = new Button("Valider");

            {
                validateButton.setOnAction(event -> handleValidateUser(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(validateButton);
                } else {
                    setGraphic(null);
                }
            }
        });

        // Ajout du bouton "Refuser"
        rejectColumn.setCellFactory(param -> new TableCell<Utilisateur, Void>() {
            private final Button rejectButton = new Button("Refuser");

            {
                rejectButton.setOnAction(event -> handleRejectUser(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(rejectButton);
                } else {
                    setGraphic(null);
                }
            }
        });

        // Charger les données des utilisateurs
        loadUserData();

        // Configuration de la ComboBox pour filtrer les rôles
        filterRoleComboBox.getItems().addAll("Tous", "Étudiant", "Enseignant");
        filterRoleComboBox.setOnAction(event -> {
            String selectedRole = filterRoleComboBox.getValue();
            List<Utilisateur> filteredList = utilisateurService.getUtilisateursByRole(selectedRole);
            studentsTable.setItems(FXCollections.observableList(filteredList));
            teachersTable.setItems(FXCollections.observableList(filteredList));
        });

        // Charger les utilisateurs en attente de validation
        loadPendingValidationData();
    }

    private void loadUserData() {
        List<Utilisateur> teachers = utilisateurService.getUtilisateursByRole("Enseignant");
        List<Utilisateur> students = utilisateurService.getUtilisateursByRole("Étudiant");

        teachersData.clear();
        studentsData.clear();
        teachersData.addAll(teachers);
        studentsData.addAll(students);

        teachersTable.setItems(teachersData);
        studentsTable.setItems(studentsData);
    }

    private void loadPendingValidationData() {
        List<Utilisateur> pendingUsers = utilisateurService.getUtilisateursNonValides();
        pendingValidationData.clear();
        pendingValidationData.addAll(pendingUsers);
        pendingValidationTable.setItems(pendingValidationData);
    }

    // Gestion de l'édition d'un enseignant
    public void handleEditTeacher(Utilisateur teacher) {
        TextInputDialog dialog = new TextInputDialog(teacher.getNom());
        dialog.setTitle("Modifier l'enseignant");
        dialog.setHeaderText("Modifier les informations de l'enseignant");
        dialog.setContentText("Nouveau nom:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nouveauNom -> {
            boolean success = utilisateurService.mettreAJourUtilisateur(teacher.getEmail(), nouveauNom, teacher.getEmail());
            if (success) {
                loadUserData();
            } else {
                showErrorAlert("Échec de la mise à jour", "Impossible de mettre à jour l'enseignant.");
            }
        });
    }

    // Gestion de la suppression d'un enseignant
    public void handleDeleteTeacher(Utilisateur teacher) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer l'enseignant");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet enseignant ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = utilisateurService.supprimerUtilisateur(teacher.getEmail());
            if (success) {
                loadUserData();
            } else {
                showErrorAlert("Échec de la suppression", "Impossible de supprimer l'enseignant.");
            }
        }
    }

    // Gestion de l'édition d'un étudiant
    public void handleEditStudent(Utilisateur student) {
        TextInputDialog dialog = new TextInputDialog(student.getNom());
        dialog.setTitle("Modifier l'étudiant");
        dialog.setHeaderText("Modifier les informations de l'étudiant");
        dialog.setContentText("Nouveau nom:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nouveauNom -> {
            boolean success = utilisateurService.mettreAJourUtilisateur(student.getEmail(), nouveauNom, student.getEmail());
            if (success) {
                loadUserData();
            } else {
                showErrorAlert("Échec de la mise à jour", "Impossible de mettre à jour l'étudiant.");
            }
        });
    }

    // Gestion de la suppression d'un étudiant
    public void handleDeleteStudent(Utilisateur student) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer l'étudiant");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet étudiant ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = utilisateurService.supprimerUtilisateur(student.getEmail());
            if (success) {
                loadUserData();
            } else {
                showErrorAlert("Échec de la suppression", "Impossible de supprimer l'étudiant.");
            }
        }
    }

    // Gestion de la validation d'un utilisateur
    private void handleValidateUser(Utilisateur user) {
        boolean success = utilisateurService.validerUtilisateur(user.getEmail());
        if (success) {
            loadPendingValidationData(); // Recharger les données après validation
            showSuccessAlert("Utilisateur validé", "L'utilisateur a été validé avec succès.");
        } else {
            showErrorAlert("Erreur", "Impossible de valider l'utilisateur.");
        }
    }

    // Gestion du refus d'un utilisateur
    private void handleRejectUser(Utilisateur user) {
        boolean success = utilisateurService.supprimerUtilisateur(user.getEmail());
        if (success) {
            loadPendingValidationData(); // Recharger les données après refus
            showSuccessAlert("Utilisateur refusé", "L'utilisateur a été refusé et supprimé.");
        } else {
            showErrorAlert("Erreur", "Impossible de refuser l'utilisateur.");
        }
    }

    // Méthode pour afficher une alerte de succès
    private void showSuccessAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Méthode pour afficher une alerte d'erreur
    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}