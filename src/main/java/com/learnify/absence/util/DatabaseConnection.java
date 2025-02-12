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
                System.out.println("Table 'absence' non trouvée, création en cours...");
                createAbsenceTable(connection);
            } else {
                System.out.println("Table 'absence' trouvée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification/création de la table : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void createAbsenceTable(Connection connection) throws SQLException {
        // On modifie la contrainte FOREIGN KEY pour référencer utilisateurs(id)
        String createTableSQL = "CREATE TABLE absence (" +
                "    absence_id INT AUTO_INCREMENT PRIMARY KEY," +
                "    student_id INT NOT NULL," +
                "    course_id INT NOT NULL," +
                "    absence_date DATE NOT NULL," +
                "    reason VARCHAR(255)," +
                "    FOREIGN KEY (student_id) REFERENCES utilisateurs(id)" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'absence' créée avec succès.");
        }
    }
}
