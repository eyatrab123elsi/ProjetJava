package com.learnify.quiz.util;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/learning_platform?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try (Connection connection = getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            // Check and create tables
            checkAndCreateTable(metaData, connection, "question", createQuestionTableSQL());
            checkAndCreateTable(metaData, connection, "user", createUserTableSQL());
            checkAndCreateTable(metaData, connection, "result", createResultTableSQL());

        } catch (SQLException e) {
            System.err.println("Error during database table check/creation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void checkAndCreateTable(DatabaseMetaData metaData, Connection connection, String tableName, String createTableSQL) throws SQLException {
        ResultSet tables = metaData.getTables(null, null, tableName, new String[]{"TABLE"});
        if (!tables.next()) {
            System.out.println(tableName + " table not found, creating it...");
            createTable(connection, createTableSQL);
        } else {
            System.out.println(tableName + " table found.");
        }
    }

    private static void createTable(Connection connection, String createTableSQL) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Table created successfully.");
        }
    }
    // SQL for the tables
    private static String createQuestionTableSQL() {
        return "CREATE TABLE question (" +
                "question_id INT AUTO_INCREMENT PRIMARY KEY," +
                "quiz_id INT," +
                "question_text TEXT NOT NULL," +
                "option_a VARCHAR(255)," +
                "option_b VARCHAR(255)," +
                "option_c VARCHAR(255)," +
                "option_d VARCHAR(255)," +
                "correct_answer_index INT NOT NULL," +
                "FOREIGN KEY (quiz_id) REFERENCES courses(course_id) ON DELETE CASCADE" +
                ")";
    }

    private static String createUserTableSQL() {
        return "CREATE TABLE user (" +
                "user_id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(255) NOT NULL," +
                "user_type VARCHAR(50) NOT NULL" +
                ")";
    }

    private static String createResultTableSQL() {
        return "CREATE TABLE result (" +
                "result_id INT AUTO_INCREMENT PRIMARY KEY," +
                "user_id INT," +
                "quiz_id INT," +
                "score INT NOT NULL," +
                 "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE," +
                 "FOREIGN KEY (quiz_id) REFERENCES courses(course_id) ON DELETE CASCADE" +
                ")";
    }
}