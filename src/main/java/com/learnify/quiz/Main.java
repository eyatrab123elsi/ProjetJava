package com.learnify.quiz;

import com.learnify.cours.controller.CoursController;
import com.learnify.quiz.controller.CreateQuestionController;

public class Main {
    public static void main(String[] args) {
        CreateQuestionController controller = new CreateQuestionController();
        controller.saveQuestion("What is Java?", "Language", "Animal", "Drink", "Car", 1); //example question
        controller.saveQuestion("What is Spring?", "Framework", "Season", "Mattress", "Color", 0); //another example
        CoursController controller1 = new CoursController();
        controller1.run();
    }
}