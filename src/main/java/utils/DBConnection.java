package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/learning_platform"; // Vérifie le nom de ta base de données
    private static final String USER = "root"; // Nom d'utilisateur MySQL (à modifier si nécessaire)
    private static final String PASSWORD = ""; // Mot de passe MySQL (à modifier si nécessaire)

    // Méthode pour obtenir la connexion
    public static Connection getConnection() {
        try {
            // Charger le driver MySQL si nécessaire
            Class.forName("com.mysql.cj.jdbc.Driver"); // Pour MySQL 8+
            // Retourner la connexion
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du driver JDBC : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
