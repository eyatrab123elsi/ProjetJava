package com.learnify.cours.util;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/learning_platform?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try (Connection connection = getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "cours", new String[]{"TABLE"});

            if (!tables.next()) {
                System.out.println("Cours table not found, creating it...");
                createCoursTable(connection);
            } else {
                System.out.println("Cours table found.");
            }
        } catch (SQLException e) {
            System.err.println("Error during database table check/creation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    private static void createCoursTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE cours (\n" +
                "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    titre VARCHAR(255) NOT NULL,\n" +
                "    description TEXT NOT NULL,\n" +
                "    duree INT NOT NULL\n" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Cours table created successfully.");
        }
    }
}
