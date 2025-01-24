package services;

import utils.DBConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException; // Ajout de l'import pour NoSuchAlgorithmException
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurService {

    // Méthode pour hacher un mot de passe avec SHA-256
    private String hacherMotDePasse(String motDePasse) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(motDePasse.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour authentifier un utilisateur
    public boolean authentifierUtilisateur(String email, String motDePasse) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                String query = "SELECT * FROM utilisateurs WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String storedPassword = rs.getString("mot_de_passe");
                    return storedPassword.equals(hacherMotDePasse(motDePasse));
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour ajouter un utilisateur
    public boolean ajouterUtilisateur(String nom, String email, String motDePasse, String role) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                String hashedPassword = hacherMotDePasse(motDePasse); // Hachage du mot de passe
                String query = "INSERT INTO utilisateurs (nom, email, mot_de_passe, role) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, nom);
                stmt.setString(2, email);
                stmt.setString(3, hashedPassword);
                stmt.setString(4, role);
                stmt.executeUpdate();
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour mettre à jour le profil de l'utilisateur
    public boolean mettreAJourProfil(String nom, String email, String newPassword) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                String hashedPassword = hacherMotDePasse(newPassword);
                String query = "UPDATE utilisateurs SET nom = ?, mot_de_passe = ? WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, nom);
                stmt.setString(2, hashedPassword);
                stmt.setString(3, email);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
