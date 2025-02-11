package com.learnify.cours.entities;

import javafx.beans.property.*;

public class Cours {
    private LongProperty id;
    private StringProperty titre;
    private StringProperty description;
    private IntegerProperty duree;
    private StringProperty pdfPath;

    public Cours() {
        this.id = new SimpleLongProperty();
        this.titre = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.duree = new SimpleIntegerProperty();
        this.pdfPath = new SimpleStringProperty();
    }

    public Cours(String titre, String description, int duree, String pdfPath) {
        this();
        this.titre.set(titre);
        this.description.set(description);
        this.duree.set(duree);
        this.pdfPath.set(pdfPath);
    }

    public long getId() {
        return id.get();
    }

    public String getTitre() {
        return titre.get();
    }

    public String getDescription() {
        return description.get();
    }

    public int getDuree() {
        return duree.get();
    }

    public String getPdfPath() {
        return pdfPath.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public void setTitre(String titre) {
        this.titre.set(titre);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setDuree(int duree) {
        this.duree.set(duree);
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath.set(pdfPath);
    }

    public LongProperty idProperty() {
        return id;
    }

    public StringProperty titreProperty() {
        return titre;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public IntegerProperty dureeProperty() {
        return duree;
    }

    public StringProperty pdfPathProperty() {
        return pdfPath;
    }
}