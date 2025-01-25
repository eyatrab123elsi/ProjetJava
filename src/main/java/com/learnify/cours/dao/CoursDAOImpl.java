package com.learnify.cours.dao;

import com.learnify.cours.model.Cours;
import com.learnify.quiz.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursDAOImpl implements CoursDAO {

    @Override
    public Cours getCoursById(Long id) {
        String query = "SELECT * FROM cours WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapCours(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cours> getAllCourses() {
        List<Cours> courses = new ArrayList<>();
        String query = "SELECT * FROM cours";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                courses.add(mapCours(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public void saveCours(Cours cours) {
        String query = "INSERT INTO cours (titre, description, duree) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, cours.getTitre());
            preparedStatement.setString(2, cours.getDescription());
            preparedStatement.setInt(3, cours.getDuree());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCours(Long id) {
        String query = "DELETE FROM cours WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Cours mapCours(ResultSet resultSet) throws SQLException {
        return new Cours(
                resultSet.getLong("id"),
                resultSet.getString("titre"),
                resultSet.getString("description"),
                resultSet.getInt("duree")
        );
    }
}
