package com.learnify.quiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.learnify.cours.controller.CoursController;
import com.learnify.quiz.controller.CreateQuestionController;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private static String css;

    @Override
    public void start(Stage stage) throws IOException {
        try {

            css = Objects.requireNonNull(getClass().getResource("/com/learnify/quiz/ui/styles.css")).toExternalForm();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/learnify/quiz/ui/login.fxml")));
            Scene scene = new Scene(root);


            applyCSS(scene);

            stage.setTitle("Learnify");
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

        CreateQuestionController controller = new CreateQuestionController();
        controller.saveQuestion("What is Java?", "Language", "Animal", "Drink", "Car", 1); // example question
        controller.saveQuestion("What is Spring?", "Framework", "Season", "Mattress", "Color", 0); // another example

        CoursController controller1 = new CoursController();

        launch();
    }
}
