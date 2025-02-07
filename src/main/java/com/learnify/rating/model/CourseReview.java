package com.learnify.rating.model;

import java.util.Objects;

public class CourseReview {

    private Long id;           // Changer l'ID de int à Long
    private Long courseId;
    private int rating;
    private String message;
    private boolean anonymous;

    // Constructeur sans ID, utilisé pour la création d'avis
    public CourseReview(Long courseId, int rating, String message, boolean anonymous) {
        this.courseId = courseId;
        this.rating = rating;
        this.message = message;
        this.anonymous = anonymous;
    }

    // Constructeur avec ID, utilisé pour la récupération depuis la DB
    public CourseReview(Long id, Long courseId, int rating, String message, boolean anonymous) {
        this.id = id;
        this.courseId = courseId;
        this.rating = rating;
        this.message = message;
        this.anonymous = anonymous;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) { // Validation de la note
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("La note doit être entre 1 et 5.");
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "CourseReview{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", rating=" + rating +
                ", message='" + message + '\'' +
                ", anonymous=" + anonymous +
                '}';
    }

    // Méthodes equals et hashCode pour une meilleure gestion des objets dans les collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseReview that = (CourseReview) o;
        return rating == that.rating &&
                anonymous == that.anonymous &&
                Objects.equals(id, that.id) &&
                Objects.equals(courseId, that.courseId) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, rating, message, anonymous);
    }
}
