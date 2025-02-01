package com.learnify.quiz.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ResultController {
    @FXML
    private TextArea resultTextArea;

    public void setResult(String result) {
        resultTextArea.setText(result);
    }
}