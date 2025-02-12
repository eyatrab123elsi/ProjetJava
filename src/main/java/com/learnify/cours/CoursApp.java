package com.learnify.cours;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class CoursApp extends Application {

    private static String css;

    @Override
    public void start(Stage stage) {
        try {

            css = Objects.requireNonNull(getClass().getResource("/cours/style.css")).toExternalForm();


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cours/RoleSelection.fxml")));
            Scene scene = new Scene(root);


            applyCSS(scene);

            stage.setTitle("Gestion des Cours");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getCss() {
        return css;
    }


    public static void applyCSS(Scene scene) {
        scene.getStylesheets().add(css);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
