package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    // Paramètres de la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/learning_platform"; // Modifie avec ton URL
    private static final String USER = "root";  // Remplace par ton utilisateur
    private static final String PASSWORD = ""; // Remplace par ton mot de passe

    // Méthode pour établir la connexion à la base de données
    public static Connection getConnection() {
        try {
            // Charger le driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn != null) {
                System.out.println("Connexion à la base de données réussie !");
            } else {
                System.out.println("Échec de la connexion à la base de données.");
            }
            return conn;
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
