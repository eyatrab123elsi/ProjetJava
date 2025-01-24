package entities;

public class Progression {
    private int id;
    private int utilisateurId;
    private int coursId;
    private double score;
    private String dateSuivi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public int getCoursId() {
        return coursId;
    }

    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getDateSuivi() {
        return dateSuivi;
    }

    public void setDateSuivi(String dateSuivi) {
        this.dateSuivi = dateSuivi;
    }

    // Getters and Setters
}
