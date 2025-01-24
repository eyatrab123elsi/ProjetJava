package com.learnify.quiz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/learning_platform?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static { 
        try (Connection connection = getConnection()) { //Connect to database
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "quiz", new String[]{"TABLE"});
            if (!tables.next()) { //check if quiz table exists 
                System.out.println("Quiz table not found, creating it...");
                createQuizTable(connection);
            } else {
                System.out.println("Quiz table found.");
            }

            tables = metaData.getTables(null, null, "question", new String[]{"TABLE"});
            if (!tables.next()) { //check if question table exists
                System.out.println("Question table not found, creating it...");
                createQuestionTable(connection);
            } else {
                System.out.println("Question table found.");
            }

        } catch (SQLException e) { //ken fama error
            System.err.println("Error during database table check/creation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //function for creating quiz table in case he dosnt exists 
    private static void createQuizTable(Connection connection) throws SQLException { 
        String createTableSQL = "CREATE TABLE quiz (\n" +
                                "    quiz_id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                                "    quiz_title VARCHAR(255) NOT NULL\n" +
                                ");";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Quiz table created successfully.");
        }
    }

    //kif quiz
    private static void createQuestionTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE question (\n" +
                                "    question_id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                                "    quiz_id INT,\n" +
                                "    question_text TEXT NOT NULL,\n" +
                                "    option_a VARCHAR(255),\n" +
                                "    option_b VARCHAR(255),\n" +
                                "    option_c VARCHAR(255),\n" +
                                "    option_d VARCHAR(255),\n" +
                                "    correct_answer_index INT NOT NULL,\n" +
                                "    FOREIGN KEY (quiz_id) REFERENCES quiz(quiz_id) ON DELETE CASCADE\n" +
                                ");";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Question table created successfully.");
        }
    }
}