package utils;

import java.sql.*;

public class DBConnection {
    private static DBConnection instance;
    private String url = "jdbc:mysql://localhost:3306/learning_platform"; // Modifie avec ton URL
    private String user = "root";  // Remplace par ton utilisateur
    private String password = ""; // Remplace par ton mot de passe
    private Connection conn;

    // Constructeur privé pour empêcher l'instanciation directe
    private DBConnection() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode getConnection() pour renvoyer la connexion
    public static Connection getConnection() {
        return getInstance().getConn();  // Appel de getConn() pour renvoyer la connexion
    }

    // Getter pour obtenir la connexion
    public Connection getConn() {
        return conn;
    }

    // Méthode pour obtenir l'instance unique de DBConnection (Singleton)
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}
