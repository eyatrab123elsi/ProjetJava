package com.learnify.rating;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RatingApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Assurez-vous que le fichier FXML est bien placé dans `resources`



                FXMLLoader loader = new FXMLLoader(getClass().getResource("/rating/CourseReviewView.fxml"));
                Parent root = loader.load();

                primaryStage.setTitle("Ajouter votre avis!");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
