package utils;

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
        try (Connection connection = getConnection()) {
            // Check and create the 'quiz' table
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "quiz", new String[]{"TABLE"});
            if (!tables.next()) {
                System.out.println("Quiz table not found, creating it...");
                createQuizTable(connection);
            } else {
                System.out.println("Quiz table found.");
            }

            // Check and create the 'question' table
            tables = metaData.getTables(null, null, "question", new String[]{"TABLE"});
            if (!tables.next()) {
                System.out.println("Question table not found, creating it...");
                createQuestionTable(connection);
            } else {
                System.out.println("Question table found.");
            }
        } catch (SQLException e) {
            System.err.println("Error during database table check/creation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to establish the database connection
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load JDBC driver
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection successful!");
            return conn;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver loading error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Method to create the 'quiz' table
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

    // Method to create the 'question' table
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

    // Main method to test the database connection
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Database connection test successful!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.err.println("Error during connection test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
