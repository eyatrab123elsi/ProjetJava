package com.learnify.cours.service;

import com.learnify.cours.entities.Cours;
import com.learnify.cours.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursServiceImpl implements CoursService {

    @Override
    public Cours getCoursById(Long id) {
        String query = "SELECT * FROM courses WHERE course_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Cours(
                        resultSet.getLong("course_id"),
                        resultSet.getString("titre"),
                        resultSet.getString("description"),
                        resultSet.getInt("duree")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cours> getAllCours() {
        List<Cours> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                courses.add(new Cours(
                        resultSet.getLong("course_id"),
                        resultSet.getString("titre"),
                        resultSet.getString("description"),
                        resultSet.getInt("duree")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public void addCours(Cours cours) {
        String query = "INSERT INTO courses (titre, description, duree) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, cours.getTitre());
            statement.setString(2, cours.getDescription());
            statement.setInt(3, cours.getDuree());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                cours.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCours(Long id) {
        String query = "DELETE FROM courses WHERE course_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
