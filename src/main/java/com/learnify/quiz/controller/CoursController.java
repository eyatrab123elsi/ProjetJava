package com.learnify.quiz.controller;

import com.learnify.quiz.model.Cours;
import com.learnify.quiz.service.CoursService;
import com.learnify.quiz.service.CoursServiceImpl;

import java.util.Scanner;

public class CoursController {
    private CoursService coursService = new CoursServiceImpl();

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Gestion des Cours ===");
            System.out.println("1. Ajouter un cours");
            System.out.println("2. Afficher tous les cours");
            System.out.println("3. Supprimer un cours");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Titre du cours : ");
                    String title = scanner.next();
                    System.out.print("Description du cours : ");
                    String description = scanner.next();

                    Cours newCours = new Cours(null, title, description);
                    coursService.addCourse(newCours);
                    System.out.println("Cours ajouté !");
                    break;

                case 2:
                    System.out.println("\nListe des cours :");
                    for (Cours course : coursService.getAllCourses()) {
                        System.out.println(course.getId() + " - " + course.getTitle() + ": " + course.getDescription());
                    }
                    break;

                case 3:
                    System.out.print("ID du cours à supprimer : ");
                    Long id = (Long) scanner.nextLong();
                    coursService.removeCourse(id);
                    System.out.println("Cours supprimé !");
                    break;

                case 4:
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Option invalide.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
