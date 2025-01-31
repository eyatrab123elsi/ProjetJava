package com.learnify.utilisateur.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.learnify.utilisateur.services.ProgressionService;


public class SuiviProgressionController {

    @FXML
    private Label progressionLabel;

    @FXML
    public void afficherProgression(int utilisateurId) {
        ProgressionService progressionService = new ProgressionService();
        String progression = progressionService.recupererProgressionUtilisateur(utilisateurId);
        progressionLabel.setText("Votre progression : " + progression);
    }
}
