package com.learnify.cours.controller;

import com.learnify.cours.entities.Cours;
import com.learnify.cours.service.CoursService;
import com.learnify.cours.service.CoursServiceImpl;

import java.util.Scanner;

public class CoursController {
    private final CoursService coursService = new CoursServiceImpl();

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
            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre valide.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character after number input

            switch (choice) {
                case 1:
                    System.out.print("Titre du cours : ");
                    String titre = scanner.nextLine();

                    System.out.print("Description du cours : ");
                    String description = scanner.nextLine();

                    System.out.print("Durée du cours (en heures) : ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Veuillez entrer une durée valide (nombre entier).");
                        scanner.next();
                    }
                    int duree = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character after number input

                    Cours newCours = new Cours(null, titre, description, duree);
                    coursService.addCours(newCours);
                    System.out.println("Cours ajouté avec succès !");
                    break;

                case 2:
                    System.out.println("\nListe des cours :");
                    var courses = coursService.getAllCours();
                    if (courses.isEmpty()) {
                        System.out.println("Aucun cours disponible.");
                    } else {
                        for (Cours course : courses) {
                            System.out.println(course.getId() + " - " + course.getTitre() + ": " + course.getDescription() + " (Durée: " + course.getDuree() + "h)");
                        }
                    }
                    break;

                case 3:
                    System.out.print("ID du cours à supprimer : ");
                    while (!scanner.hasNextLong()) {
                        System.out.println("Veuillez entrer un ID valide.");
                        scanner.next();
                    }
                    Long id = scanner.nextLong();
                    scanner.nextLine(); // Consume newline character after number input

                    Cours coursToDelete = coursService.getCoursById(id);
                    if (coursToDelete != null) {
                        coursService.deleteCours(id);
                        System.out.println("Cours supprimé avec succès !");
                    } else {
                        System.out.println("Aucun cours trouvé avec l'ID : " + id);
                    }
                    break;

                case 4:
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
