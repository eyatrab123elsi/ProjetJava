package com.learnify.cours.service;

import com.learnify.cours.entities.Cours;
import com.learnify.cours.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursServiceImpl implements CoursService {

    @Override
    public Cours getCoursById(Long id) {
        String query = "SELECT * FROM cours WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Cours(
                        resultSet.getLong("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("description"),
                        resultSet.getInt("duree")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching course by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cours> getAllCours() {
        String query = "SELECT * FROM cours";
        List<Cours> courses = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                courses.add(new Cours(
                        resultSet.getLong("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("description"),
                        resultSet.getInt("duree")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all courses: " + e.getMessage());
            e.printStackTrace();
        }

        return courses;
    }

    @Override
    public void addCours(Cours cours) {
        String query = "INSERT INTO cours (titre, description, duree) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cours.getTitre());
            statement.setString(2, cours.getDescription());
            statement.setInt(3, cours.getDuree());
            statement.executeUpdate();

            System.out.println("Course added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding course: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCours(Long id) {
        String query = "DELETE FROM cours WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.executeUpdate();

            System.out.println("Course deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error deleting course: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
