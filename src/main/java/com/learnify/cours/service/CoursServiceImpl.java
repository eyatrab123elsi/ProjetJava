package com.learnify.cours.service;

import com.learnify.cours.entities.Cours;
import com.learnify.cours.util.DatabaseConnection;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursServiceImpl implements CoursService {

    @Override
    public void addCours(Cours cours, File pdfFile) {
        String query = "INSERT INTO courses (titre, description, duree, pdf_path) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            String pdfPath = savePDFToLocal(pdfFile);

            statement.setString(1, cours.getTitre());
            statement.setString(2, cours.getDescription());
            statement.setInt(3, cours.getDuree());
            statement.setString(4, pdfPath);

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                cours.setId(generatedKeys.getLong(1));
                cours.setPdfPath(pdfPath);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCours(Long id) {
        String selectQuery = "SELECT pdf_path FROM courses WHERE course_id = ?";
        String deleteQuery = "DELETE FROM courses WHERE course_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {

            selectStatement.setLong(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                String pdfPath = resultSet.getString("pdf_path");
                if (pdfPath != null) {
                    File pdfFile = new File(pdfPath);
                    if (pdfFile.exists()) {
                        pdfFile.delete();
                    }
                }
            }

            deleteStatement.setLong(1, id);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cours> getAllCours() {
        List<Cours> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int duree = resultSet.getInt("duree");
                courses.add(new Cours(
                        resultSet.getString("titre"),
                        resultSet.getString("description"),
                        duree,
                        resultSet.getString("pdf_path")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    private String savePDFToLocal(File pdfFile) {
        if (pdfFile == null) return null;

        File destDir = new File("pdf_lessons");
        if (!destDir.exists()) destDir.mkdirs();

        File destFile = new File(destDir, pdfFile.getName());
        try {
            Files.copy(pdfFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}