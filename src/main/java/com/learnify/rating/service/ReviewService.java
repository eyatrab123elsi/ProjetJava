package com.learnify.rating.service;
import com.learnify.rating.entities.CourseReview;
import com.learnify.rating.controller.ReviewController;
import com.learnify.cours.entities.Cours;
import com.learnify.cours.service.CoursService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class ReviewService {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public void saveReview(CourseReview review) {
        String sql = "INSERT INTO course_reviews (course_id, rating, message, created_at, anonymous) VALUES (?, ?, ?, NOW(), ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, review.getCourseId());
            stmt.setInt(2, review.getRating());
            stmt.setString(3, review.getMessage());
            stmt.setBoolean(4, review.isAnonymous());

            stmt.executeUpdate();
            System.out.println("Évaluation enregistrée : " + review);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
