package com.learnify.utilisateur.entities;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utilisateur {
    private IntegerProperty id; // Ajout de l'ID
    private StringProperty nom;
    private StringProperty prenom;
    private StringProperty email;
    private StringProperty telephone;
    private StringProperty adresse;
    private StringProperty motDePasse;
    private StringProperty role;
    private ObjectProperty<LocalDate> dateNaissance;


    // Constructeur avec ID
    public Utilisateur(int id, String nom, String prenom, String email, String telephone, String adresse, LocalDate dateNaissance, String motDePasse, String role) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.email = new SimpleStringProperty(email);
        this.telephone = new SimpleStringProperty(telephone);
        this.adresse = new SimpleStringProperty(adresse);
        this.dateNaissance = new SimpleObjectProperty<>(dateNaissance);
        this.motDePasse = new SimpleStringProperty(motDePasse);
        this.role = new SimpleStringProperty(role);
    }

    // Constructeur sans ID (si nécessaire)
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

    // Getters
    public int getId() { return id.get(); } // Getter pour l'ID
    public String getNom() { return nom.get(); }
    public String getPrenom() { return prenom.get(); }
    public String getEmail() { return email.get(); }
    public String getTelephone() { return telephone.get(); }
    public String getAdresse() { return adresse.get(); }
    public String getMotDePasse() { return motDePasse.get(); }
    public String getRole() { return role.get(); }
    public LocalDate getDateNaissance() { return dateNaissance.get(); }

    // Setters
    public void setId(int id) { this.id.set(id); } // Setter pour l'ID
    public void setNom(String nom) { this.nom.set(nom); }
    public void setPrenom(String prenom) { this.prenom.set(prenom); }
    public void setEmail(String email) { this.email.set(email); }
    public void setTelephone(String telephone) { this.telephone.set(telephone); }
    public void setAdresse(String adresse) { this.adresse.set(adresse); }
    public void setMotDePasse(String motDePasse) { this.motDePasse.set(motDePasse); }
    public void setRole(String role) { this.role.set(role); }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance.set(dateNaissance); }

    // Properties pour JavaFX
    public IntegerProperty idProperty() { return id; } // Property pour l'ID
    public StringProperty nomProperty() { return nom; }
    public StringProperty prenomProperty() { return prenom; }
    public StringProperty emailProperty() { return email; }
    public StringProperty telephoneProperty() { return telephone; }
    public StringProperty adresseProperty() { return adresse; }
    public StringProperty roleProperty() { return role; }
    public ObjectProperty<LocalDate> dateNaissanceProperty() { return dateNaissance; }

    // Formatage de la date
    public String getFormattedDateNaissance() {
        return (dateNaissance.get() != null) ? dateNaissance.get().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Non défini";
    }
}