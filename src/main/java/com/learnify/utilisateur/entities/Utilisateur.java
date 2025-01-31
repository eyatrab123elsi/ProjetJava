package com.learnify.utilisateur.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ObjectProperty;
import java.time.LocalDate;

public class Utilisateur {

    private StringProperty nom;
    private StringProperty prenom;
    private StringProperty email;
    private StringProperty telephone;
    private StringProperty adresse;

    private StringProperty motDePasse;
    private StringProperty role;
    private ObjectProperty<LocalDate> dateNaissance; // Si vous souhaitez utiliser LocalDate

    // Constructeur avec nom, email et rôle
    public Utilisateur(String nom, String email, String role) {
        this.nom = new SimpleStringProperty(nom);
        this.email = new SimpleStringProperty(email);
        this.role = new SimpleStringProperty(role);

        // Initialiser les autres propriétés avec des valeurs par défaut
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
        this.motDePasse = new SimpleStringProperty(motDePasse);
        this.role = new SimpleStringProperty(role);
        this.dateNaissance = new SimpleObjectProperty<>(dateNaissance);
    }

    // Getter et setter pour chaque propriété
    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getPrenom() {
        return prenom.get();
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public StringProperty telephoneProperty() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getAdresse() {
        return adresse.get();
    }

    public StringProperty adresseProperty() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    public String getMotDePasse() {
        return motDePasse.get();
    }

    public StringProperty motDePasseProperty() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse.set(motDePasse);
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public LocalDate getDateNaissance() {
        return dateNaissance.get();
    }

    public ObjectProperty<LocalDate> dateNaissanceProperty() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance.set(dateNaissance);
    }
}
