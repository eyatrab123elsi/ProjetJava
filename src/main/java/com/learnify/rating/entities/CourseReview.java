package com.learnify.rating.entities;
import java.time.LocalDateTime;


public class CourseReview {
    private int id;
    private int courseId;
    private int rating;
    private String message;
    private LocalDateTime createdAt;
    private boolean anonymous;

    public CourseReview(int courseId, int rating, String message, boolean anonymous) {
        this.courseId = courseId;
        this.rating = rating;
        this.message = message;
        this.anonymous = anonymous;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getRating() {
        return rating;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isAnonymous() {
        return anonymous;
    }
}
