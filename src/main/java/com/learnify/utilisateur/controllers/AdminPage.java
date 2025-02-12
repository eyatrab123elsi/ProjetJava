package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.learnify.utilisateur.entities.Utilisateur;
import com.learnify.utilisateur.services.UtilisateurService;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AdminPage {
    @FXML private Label welcomeLabel;
    @FXML private Button logoutButton;
    @FXML private TableView<Utilisateur> teachersTable;
    @FXML private TableView<Utilisateur> studentsTable;
    @FXML private TableColumn<Utilisateur, String> nameColumn, emailColumn, prenomColumn, telephoneColumn, adresseColumn, dateNaissanceColumn, roleColumn;
    @FXML private TableColumn<Utilisateur, String> nameColumnStudent, emailColumnStudent, prenomColumnStudent, telephoneColumnStudent, adresseColumnStudent, dateNaissanceColumnStudent, roleColumnStudent;
    @FXML private TableColumn<Utilisateur, Void> actionColumn, actionColumnStudent;
    @FXML private ComboBox<String> filterRoleComboBox;

    private ObservableList<Utilisateur> teachersData = FXCollections.observableArrayList();
    private ObservableList<Utilisateur> studentsData = FXCollections.observableArrayList();
    private final UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    public void initialize() {
        welcomeLabel.setText("Welcome to the administration page !");

        // Configuration des colonnes
        configureColumns(teachersTable, nameColumn, emailColumn, prenomColumn, telephoneColumn, adresseColumn, dateNaissanceColumn, roleColumn);
        configureColumns(studentsTable, nameColumnStudent, emailColumnStudent, prenomColumnStudent, telephoneColumnStudent, adresseColumnStudent, dateNaissanceColumnStudent, roleColumnStudent);

        // Configuration des colonnes d'action avec icônes
        configureActionColumn(actionColumn, this::handleEditUser, this::handleDeleteUser, this::handleValidateUser);
        configureActionColumn(actionColumnStudent, this::handleEditUser, this::handleDeleteUser, this::handleValidateUser);

        // Chargement des données
        loadUserData();

        // Configuration de la ComboBox pour filtrer les rôles
        filterRoleComboBox.getItems().addAll("Tous", "Étudiant", "Enseignant");
        filterRoleComboBox.setOnAction(event -> filterUsersByRole());

        // Connexion du bouton de déconnexion
        logoutButton.setOnAction(event -> handleLogout());

        // Appliquer l'alternance des couleurs des lignes
        applyRowColorAlternation(teachersTable);
        applyRowColorAlternation(studentsTable);
    }

    @FXML
    private void handleLogout() {
        System.out.println("Déconnexion réussie !");

        // Fermer la fenêtre actuelle
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();

        // Ouvrir la page d'authentification
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Authentification.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("Authentification");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Erreur", "Impossible de charger la page d'authentification.");
        }
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

    private void configureActionColumn(TableColumn<Utilisateur, Void> column, UserActionHandler editHandler, UserActionHandler deleteHandler, UserActionHandler validateHandler) {
        column.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button();
            private final Button deleteButton = new Button();
            private final Button validateButton = new Button();

            {
                // Charger les icônes
                Image editIcon = new Image(getClass().getResourceAsStream("/image/edit11.png"));
                Image deleteIcon = new Image(getClass().getResourceAsStream("/image/1.png"));
                Image validateIcon = new Image(getClass().getResourceAsStream("/image/validate1.png"));

                // Redimensionner les icônes
                ImageView editView = new ImageView(editIcon);
                editView.setFitWidth(30);
                editView.setFitHeight(30);

                ImageView deleteView = new ImageView(deleteIcon);
                deleteView.setFitWidth(26);
                deleteView.setFitHeight(26);

                ImageView validateView = new ImageView(validateIcon);
                validateView.setFitWidth(26);
                validateView.setFitHeight(26);

                // Associer les icônes aux boutons
                editButton.setGraphic(editView);
                deleteButton.setGraphic(deleteView);
                validateButton.setGraphic(validateView);

                // Style des boutons (optionnel)
                editButton.setStyle("-fx-background-color: transparent;");
                deleteButton.setStyle("-fx-background-color: transparent;");
                validateButton.setStyle("-fx-background-color: transparent;");

                // Actions des boutons
                editButton.setOnAction(event -> {
                    Utilisateur user = getTableRow().getItem();
                    if (user != null) {
                        editHandler.handle(user);
                    }
                });

                deleteButton.setOnAction(event -> {
                    Utilisateur user = getTableRow().getItem();
                    if (user != null) {
                        deleteHandler.handle(user);
                    }
                });

                validateButton.setOnAction(event -> {
                    Utilisateur user = getTableRow().getItem();
                    if (user != null) {
                        validateHandler.handle(user);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Utilisateur user = getTableRow().getItem();
                    if (user != null && user.isEstValide()) {
                        // Si l'utilisateur est déjà validé, ne pas afficher le bouton "Valider"
                        HBox hbox = new HBox(3, editButton, deleteButton);
                        setGraphic(hbox);
                    } else {
                        HBox hbox = new HBox(3, editButton, deleteButton, validateButton);
                        setGraphic(hbox);
                    }
                }
            }
        });
    }

    private void loadUserData() {
        teachersData.setAll(utilisateurService.getUtilisateursByRole("Enseignant"));
        studentsData.setAll(utilisateurService.getUtilisateursByRole("Étudiant"));
        teachersTable.setItems(teachersData);
        studentsTable.setItems(studentsData);
    }

    private void filterUsersByRole() {
        String selectedRole = filterRoleComboBox.getValue();
        teachersTable.setVisible(selectedRole.equals("Tous") || selectedRole.equals("Enseignant"));
        studentsTable.setVisible(selectedRole.equals("Tous") || selectedRole.equals("Étudiant"));
    }

    private void handleEditUser(Utilisateur user) {
        Dialog<Utilisateur> dialog = createEditUserDialog(user);
        dialog.showAndWait().ifPresent(updatedUser -> {
            if (updatedUser != null) {
                boolean success = utilisateurService.mettreAJourUtilisateur(updatedUser);
                if (success) {
                    loadUserData(); // Recharger les données après modification
                    showSuccessAlert("Succès", "L'utilisateur a été modifié avec succès !");
                } else {
                    showErrorAlert("Échec de la mise à jour", "Impossible de mettre à jour l'utilisateur.");
                }
            }
        });
    }

    private Dialog<Utilisateur> createEditUserDialog(Utilisateur user) {
        Dialog<Utilisateur> dialog = new Dialog<>();
        dialog.setTitle("Modifier l'utilisateur");

        // Ajouter un titre avec texte en gras
        dialog.setHeaderText(null);
        Label headerLabel = new Label("Modifier les informations de l'utilisateur");
        headerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        dialog.getDialogPane().setHeader(headerLabel);

        ButtonType saveButtonType = new ButtonType("Sauvegarder", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Appliquer un fond dégradé aux boutons
        Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setStyle("-fx-background-color: linear-gradient(to right, #c999ed, #8bbbf8); -fx-text-fill: #000000;");

        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: linear-gradient(to right, #c999ed, #8bbbf8); -fx-text-fill: #000000;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Champs de texte avec bordure violette
        TextField nomField = new TextField(user.getNom());
        nomField.setStyle("-fx-border-color: #8d97f6;");

        TextField prenomField = new TextField(user.getPrenom());
        prenomField.setStyle("-fx-border-color: #8d97f6;");

        TextField emailField = new TextField(user.getEmail());
        emailField.setStyle("-fx-border-color: #8d97f6;");

        TextField telephoneField = new TextField(user.getTelephone());
        telephoneField.setStyle("-fx-border-color: #8d97f6;");

        TextField adresseField = new TextField(user.getAdresse());
        adresseField.setStyle("-fx-border-color: #8d97f6;");

        DatePicker dateNaissancePicker = new DatePicker(user.getDateNaissance());

        // Ajouter les champs Nom et Prénom
        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);

        grid.add(new Label("Prénom:"), 0, 1);
        grid.add(prenomField, 1, 1);

        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);

        grid.add(new Label("Téléphone:"), 0, 3);
        grid.add(telephoneField, 1, 3);

        grid.add(new Label("Adresse:"), 0, 4);
        grid.add(adresseField, 1, 4);

        grid.add(new Label("Date de naissance:"), 0, 5);
        grid.add(dateNaissancePicker, 1, 5);

        dialog.getDialogPane().setContent(grid);

        // Validation des champs
        saveButton.setDisable(true);

        // Contrôle de saisie pour le nom, prénom et adresse (doivent être de type texte)
        nomField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]*")) {
                nomField.setText(oldValue);
            }
            validateFields(nomField, prenomField, adresseField, telephoneField, emailField, saveButton);
        });

        prenomField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]*")) {
                prenomField.setText(oldValue);
            }
            validateFields(nomField, prenomField, adresseField, telephoneField, emailField, saveButton);
        });

        adresseField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z ]*")) {  // Seules les lettres et les espaces sont autorisés
                adresseField.setText(oldValue);
            }
            validateFields(nomField, prenomField, adresseField, telephoneField, emailField, saveButton);
        });

        // Contrôle de saisie pour le téléphone (doit être de type numérique)
        telephoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                telephoneField.setText(oldValue);
            }
            validateFields(nomField, prenomField, adresseField, telephoneField, emailField, saveButton);
        });

        // Contrôle de saisie pour l'email (doit contenir @gmail.com)
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
                emailField.setText(oldValue);
            }
            validateFields(nomField, prenomField, adresseField, telephoneField, emailField, saveButton);
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                // Vérifier si l'email existe déjà dans la base de données
                if (!user.getEmail().equals(emailField.getText()) && utilisateurService.emailExiste(emailField.getText())) {
                    showErrorAlert("Erreur", "L'email existe déjà dans la base de données.");
                    return null;
                }

                user.setNom(nomField.getText());
                user.setPrenom(prenomField.getText());
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

    private void validateFields(TextField nomField, TextField prenomField, TextField adresseField, TextField telephoneField, TextField emailField, Button saveButton) {
        boolean isValid = !nomField.getText().isEmpty() &&
                !prenomField.getText().isEmpty() &&
                !adresseField.getText().isEmpty() &&
                !telephoneField.getText().isEmpty() &&
                emailField.getText().matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$");

        saveButton.setDisable(!isValid);
    }

    private void handleDeleteUser(Utilisateur user) {
        showConfirmationDialog("Supprimer l'utilisateur", "Êtes-vous sûr de vouloir supprimer cet utilisateur ?")
                .ifPresent(buttonType -> {
                    if (buttonType == ButtonType.OK) {
                        boolean success = utilisateurService.supprimerUtilisateur(user.getEmail());
                        if (success) {
                            loadUserData(); // Recharger les données après suppression
                            showSuccessAlert("Succès", "L'utilisateur a été supprimé avec succès !");
                        } else {
                            showErrorAlert("Échec de la suppression", "Impossible de supprimer l'utilisateur.");
                        }
                    }
                });
    }

    private void handleValidateUser(Utilisateur user) {
        boolean success = utilisateurService.validerUtilisateur(user.getEmail());
        if (success) {
            loadUserData(); // Recharger les données après validation
            showSuccessAlert("Succès", "L'utilisateur a été validé avec succès !");
        } else {
            showErrorAlert("Échec de la validation", "Impossible de valider l'utilisateur.");
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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

    private void applyRowColorAlternation(TableView<Utilisateur> table) {
        table.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Utilisateur item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setBackground(Background.EMPTY);
                } else {
                    if (getIndex() % 2 == 0) {
                        setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, null)));
                    } else {
                        setBackground(new Background(new BackgroundFill(Color.rgb(204, 204, 204), CornerRadii.EMPTY, null)));
                    }
                }
            }
        });
    }

    @FunctionalInterface
    private interface UserActionHandler {
        void handle(Utilisateur user);
    }
}
