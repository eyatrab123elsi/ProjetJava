package utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        // Tester la connexion à la base de données
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Connexion à la base de données réussie !");
            } else {
                System.out.println("Échec de la connexion à la base de données.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la tentative de connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
