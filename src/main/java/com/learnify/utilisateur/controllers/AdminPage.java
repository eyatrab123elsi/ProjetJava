package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.learnify.utilisateur.entities.Utilisateur;
import com.learnify.utilisateur.services.UtilisateurService;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AdminPage {
    @FXML private Label welcomeLabel;
    @FXML private TableView<Utilisateur> teachersTable;
    @FXML private TableView<Utilisateur> studentsTable;
    @FXML private TableView<Utilisateur> pendingValidationTable;
    @FXML private TableColumn<Utilisateur, String> nameColumn, emailColumn, prenomColumn, telephoneColumn, adresseColumn, dateNaissanceColumn, roleColumn;
    @FXML private TableColumn<Utilisateur, String> nameColumnStudent, emailColumnStudent, prenomColumnStudent, telephoneColumnStudent, adresseColumnStudent, dateNaissanceColumnStudent, roleColumnStudent;
    @FXML private TableColumn<Utilisateur, String> nameColumnPending, emailColumnPending, prenomColumnPending, telephoneColumnPending, adresseColumnPending, dateNaissanceColumnPending, roleColumnPending;
    @FXML private TableColumn<Utilisateur, Void> editColumn, deleteColumn, editColumnStudent, deleteColumnStudent, validateColumn, rejectColumn;
    @FXML private ComboBox<String> filterRoleComboBox;

    private ObservableList<Utilisateur> teachersData = FXCollections.observableArrayList();
    private ObservableList<Utilisateur> studentsData = FXCollections.observableArrayList();
    private ObservableList<Utilisateur> pendingValidationData = FXCollections.observableArrayList();
    private final UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    public void initialize() {
        welcomeLabel.setText("Bienvenue sur la page d'administration !");

        // Configuration des colonnes
        configureColumns(teachersTable, nameColumn, emailColumn, prenomColumn, telephoneColumn, adresseColumn, dateNaissanceColumn, roleColumn);
        configureColumns(studentsTable, nameColumnStudent, emailColumnStudent, prenomColumnStudent, telephoneColumnStudent, adresseColumnStudent, dateNaissanceColumnStudent, roleColumnStudent);
        configureColumns(pendingValidationTable, nameColumnPending, emailColumnPending, prenomColumnPending, telephoneColumnPending, adresseColumnPending, dateNaissanceColumnPending, roleColumnPending);

        // Configuration des boutons d'action avec des couleurs
        configureActionColumn(editColumn, "Modifier", "blue", this::handleEditUser);
        configureActionColumn(deleteColumn, "Supprimer", "red", this::handleDeleteUser);
        configureActionColumn(editColumnStudent, "Modifier", "blue", this::handleEditUser);
        configureActionColumn(deleteColumnStudent, "Supprimer", "red", this::handleDeleteUser);
        configureActionColumn(validateColumn, "Valider", "green", this::handleValidateUser);
        configureActionColumn(rejectColumn, "Rejeter", "orange", this::handleRejectUser);

        // Chargement des données
        loadUserData();
        loadPendingValidationData();

        // Configuration de la ComboBox pour filtrer les rôles
        filterRoleComboBox.getItems().addAll("Tous", "Étudiant", "Enseignant");
        filterRoleComboBox.setOnAction(event -> filterUsersByRole());
    }

    private void configureColumns(TableView<Utilisateur> table, TableColumn<Utilisateur, String> nameColumn, TableColumn<Utilisateur, String> emailColumn, TableColumn<Utilisateur, String> prenomColumn, TableColumn<Utilisateur, String> telephoneColumn, TableColumn<Utilisateur, String> adresseColumn, TableColumn<Utilisateur, String> dateNaissanceColumn, TableColumn<Utilisateur, String> roleColumn) {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        telephoneColumn.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
        adresseColumn.setCellValueFactory(cellData -> cellData.getValue().adresseProperty());
        dateNaissanceColumn.setCellValueFactory(cellData -> {
            LocalDate date = cellData.getValue().getDateNaissance();
            String formattedDate = (date != null) ? date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Non défini";
            return new SimpleStringProperty(formattedDate);
        });
        roleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));
    }

    private void configureActionColumn(TableColumn<Utilisateur, Void> column, String buttonText, String color, UserActionHandler handler) {
        column.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button(buttonText);

            {
                button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white;");
                button.setOnAction(event -> {
                    Utilisateur user = getTableRow().getItem();
                    if (user != null) {
                        handler.handle(user);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : button);
            }
        });
    }

    private void loadUserData() {
        teachersData.setAll(utilisateurService.getUtilisateursByRole("Enseignant"));
        studentsData.setAll(utilisateurService.getUtilisateursByRole("Étudiant"));
        teachersTable.setItems(teachersData);
        studentsTable.setItems(studentsData);
    }

    private void loadPendingValidationData() {
        pendingValidationData.setAll(utilisateurService.getUtilisateursNonValides());
        pendingValidationTable.setItems(pendingValidationData);
    }

    private void filterUsersByRole() {
        String selectedRole = filterRoleComboBox.getValue();
        teachersTable.setVisible(selectedRole.equals("Tous") || selectedRole.equals("Enseignant"));
        studentsTable.setVisible(selectedRole.equals("Tous") || selectedRole.equals("Étudiant"));
    }

    private void handleEditUser(Utilisateur user) {
        Dialog<Utilisateur> dialog = createEditUserDialog(user);
        dialog.showAndWait().ifPresent(updatedUser -> {
            boolean success = utilisateurService.mettreAJourUtilisateur(updatedUser);
            if (success) {
                loadUserData();
            } else {
                showErrorAlert("Échec de la mise à jour", "Impossible de mettre à jour l'utilisateur.");
            }
        });
    }

    private Dialog<Utilisateur> createEditUserDialog(Utilisateur user) {
        Dialog<Utilisateur> dialog = new Dialog<>();
        dialog.setTitle("Modifier l'utilisateur");
        dialog.setHeaderText("Modifier les informations de l'utilisateur");

        ButtonType saveButtonType = new ButtonType("Sauvegarder", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField emailField = new TextField(user.getEmail());
        TextField telephoneField = new TextField(user.getTelephone());
        TextField adresseField = new TextField(user.getAdresse());
        DatePicker dateNaissancePicker = new DatePicker(user.getDateNaissance());

        grid.add(new Label("Email:"), 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(new Label("Téléphone:"), 0, 1);
        grid.add(telephoneField, 1, 1);
        grid.add(new Label("Adresse:"), 0, 2);
        grid.add(adresseField, 1, 2);
        grid.add(new Label("Date de naissance:"), 0, 3);
        grid.add(dateNaissancePicker, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                user.setEmail(emailField.getText());
                user.setTelephone(telephoneField.getText());
                user.setAdresse(adresseField.getText());
                user.setDateNaissance(dateNaissancePicker.getValue());
                return user;
            }
            return null;
        });

        return dialog;
    }

    private void handleDeleteUser(Utilisateur user) {
        showConfirmationDialog("Supprimer l'utilisateur", "Êtes-vous sûr de vouloir supprimer cet utilisateur ?")
                .ifPresent(buttonType -> {
                    if (buttonType == ButtonType.OK) {
                        boolean success = utilisateurService.supprimerUtilisateur(user.getEmail());
                        if (success) {
                            loadUserData();
                        } else {
                            showErrorAlert("Échec de la suppression", "Impossible de supprimer l'utilisateur.");
                        }
                    }
                });
    }

    private void handleValidateUser(Utilisateur user) {
        boolean success = utilisateurService.validerUtilisateur(user.getEmail());
        if (success) {
            loadPendingValidationData();
        } else {
            showErrorAlert("Échec de la validation", "Impossible de valider l'utilisateur.");
        }
    }

    private void handleRejectUser(Utilisateur user) {
        boolean success = utilisateurService.rejeterUtilisateur(user.getEmail());
        if (success) {
            loadPendingValidationData();
        } else {
            showErrorAlert("Échec du rejet", "Impossible de rejeter l'utilisateur.");
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmationDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    @FunctionalInterface
    private interface UserActionHandler {
        void handle(Utilisateur user);
    }
}