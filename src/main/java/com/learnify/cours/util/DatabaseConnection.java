package com.learnify.cours.util;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/learning_platform?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";


    static {
        try (Connection connection = getConnection()) {
            if (!isTableExists(connection, "courses")) {
                System.out.println("Table 'courses' non trouvée, création en cours...");
                createCoursesTable(connection);
            } else {
                System.out.println("Table 'courses' déjà existante.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion ou création de la table: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    private static boolean isTableExists(Connection connection, String tableName) throws SQLException {
        try (ResultSet tables = connection.getMetaData().getTables(null, null, tableName, new String[]{"TABLE"})) {
            return tables.next();
        }
    }


    private static void createCoursesTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE courses (" +
                "course_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "titre VARCHAR(255) NOT NULL, " +
                "description TEXT NOT NULL, " +
                "duree INT NOT NULL" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'courses' créée avec succès.");
        }
    }
}
