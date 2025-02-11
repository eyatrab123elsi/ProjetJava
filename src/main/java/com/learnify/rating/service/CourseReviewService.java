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

            stmt.setLong(1, review.getCourseId());
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
    public List<CourseReview> getReviewsForCourse(Long courseId) {
        List<CourseReview> reviews = new ArrayList<>();
        String selectSQL = "SELECT * FROM course_reviews WHERE course_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {

            stmt.setLong(1, courseId);
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

    // Méthode pour supprimer un avis
    public boolean deleteReview(Long reviewId) {
        String deleteSQL = "DELETE FROM course_reviews WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

            stmt.setLong(1, reviewId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'avis : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour mettre à jour un avis
    public boolean updateReview(CourseReview review) {
        String updateSQL = "UPDATE course_reviews SET rating = ?, message = ?, anonymous = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

            stmt.setInt(1, review.getRating());  // Mise à jour de la note
            stmt.setString(2, review.getMessage());  // Mise à jour du message
            stmt.setBoolean(3, review.isAnonymous());  // Mise à jour de l'anonymat
            stmt.setLong(4, review.getId());  // Identification de l'avis à mettre à jour

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'avis : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
