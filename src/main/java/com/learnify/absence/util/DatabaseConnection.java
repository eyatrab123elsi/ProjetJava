package com.learnify.absence.util;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/learning_platform?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try (Connection connection = getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "absence", new String[]{"TABLE"});

            if (!tables.next()) {
                System.out.println("Absence table not found, creating it...");
                createAbsenceTable(connection);
            } else {
                System.out.println("Absence table found.");
            }
        } catch (SQLException e) {
            System.err.println("Error during database table check/creation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void createAbsenceTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE absence (" +
                "    absence_id INT AUTO_INCREMENT PRIMARY KEY," +
                "    student_id INT NOT NULL," +
                "    course_id INT NOT NULL," +
                "    absence_date DATE NOT NULL," +
                "    reason VARCHAR(255)," +
                "    FOREIGN KEY (student_id) REFERENCES students(student_id)," +
                "    FOREIGN KEY (course_id) REFERENCES courses(course_id)" +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Absence table created successfully.");
        }
    }
}
