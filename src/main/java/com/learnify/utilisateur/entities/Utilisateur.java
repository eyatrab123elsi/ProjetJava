package com.learnify.utilisateur.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ObjectProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utilisateur {

    private StringProperty nom;
    private StringProperty prenom;
    private StringProperty email;
    private StringProperty telephone;
    private StringProperty adresse;
    private StringProperty motDePasse;
    private StringProperty role;
    private ObjectProperty<LocalDate> dateNaissance;

    // Constructeur avec nom, email et rôle
    public Utilisateur(String nom, String email, String role) {
        this.nom = new SimpleStringProperty(nom);
        this.email = new SimpleStringProperty(email);
        this.role = new SimpleStringProperty(role);
        this.prenom = new SimpleStringProperty(""); // Par exemple, vide
        this.telephone = new SimpleStringProperty(""); // Par exemple, vide
        this.adresse = new SimpleStringProperty(""); // Par exemple, vide
        this.motDePasse = new SimpleStringProperty(""); // Par exemple, vide
        this.dateNaissance = new SimpleObjectProperty<>(null); // Par exemple, null
    }

    // Constructeur complet avec tous les champs
    public Utilisateur(String nom, String prenom, String email, String telephone, String adresse, LocalDate dateNaissance, String motDePasse, String role) {
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.email = new SimpleStringProperty(email);
        this.telephone = new SimpleStringProperty(telephone);
        this.adresse = new SimpleStringProperty(adresse);
        this.dateNaissance = new SimpleObjectProperty<>(dateNaissance);
        this.motDePasse = new SimpleStringProperty(motDePasse);
        this.role = new SimpleStringProperty(role);
    }

    // Getters et setters pour chaque propriété
    public String getNom() {
        return nom.get();
    }

    public String getPrenom() {
        return prenom.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getTelephone() {
        return telephone.get();
    }

    public String getAdresse() {
        return adresse.get();
    }

    public String getMotDePasse() {
        return motDePasse.get();
    }

    public String getRole() {
        return role.get();
    }

    public LocalDate getDateNaissance() {
        return dateNaissance.get();
    }

    // Formatage de la date de naissance
    public String getFormattedDateNaissance() {
        if (dateNaissance.get() != null) {
            return dateNaissance.get().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return "Date non définie"; // Retourner une valeur par défaut si la date est null
    }

    // Méthodes pour les propriétés StringProperty
    public StringProperty nomProperty() {
        return nom;
    }

    public StringProperty emailProperty() {
        return email;
    }
}
