package com.learnify.rating.service;

import com.learnify.rating.model.CourseReview;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseReviewService {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/learning_platform";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Sauvegarde un avis
    public void saveReview(CourseReview review) {
        String insertSQL = "INSERT INTO course_reviews (course_id, rating, message, anonymous) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

            stmt.setLong(1, review.getCourseId());  // Ajout du champ course_id
            stmt.setInt(2, review.getRating());
            stmt.setString(3, review.getMessage());
            stmt.setBoolean(4, review.isAnonymous());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("L'avis a été ajouté avec succès !");
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement de l'avis : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Récupère les avis pour un cours donné
    public List<CourseReview> getReviewsForCourse(int courseId) {
        List<CourseReview> reviews = new ArrayList<>();
        String selectSQL = "SELECT * FROM course_reviews WHERE course_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {

            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CourseReview review = new CourseReview(
                        rs.getLong("course_id"),
                        rs.getInt("rating"),
                        rs.getString("message"),
                        rs.getBoolean("anonymous")
                );
                review.setId(rs.getLong("id"));
                reviews.add(review);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des avis : " + e.getMessage());
            e.printStackTrace();
        }

        return reviews;
    }

    // Récupère les titres de tous les cours disponibles
    public List<String> getAllCourses() {
        List<String> courseTitles = new ArrayList<>();
        String selectSQL = "SELECT titre FROM courses";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courseTitles.add(rs.getString("titre"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des cours : " + e.getMessage());
            e.printStackTrace();
        }

        return courseTitles;
    }
}
