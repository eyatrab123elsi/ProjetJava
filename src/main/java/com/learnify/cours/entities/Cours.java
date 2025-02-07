package com.learnify.cours.entities;

public class Cours {
    private Long course_id;
    private String titre;
    private String description;
    private int duree;

    public Cours(Long course_id, String titre, String description, int duree) {
        this.course_id =course_id;
        this.titre = titre;
        this.description = description;
        this.duree = duree;
    }


    public Long getId() {
        return course_id;
    }    

    public void setId(Long course_id) {
        this.course_id = course_id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
}
