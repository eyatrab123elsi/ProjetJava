package services;

import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurService {

    private Connection conn;

    public UtilisateurService() {
        // Utiliser getConn() au lieu de getConnection()
        this.conn = DBConnection.getInstance().getConn();
    }

    public boolean ajouterUtilisateur(String nom, String email, String motDePasse, String role) {
        String query = "INSERT INTO utilisateurs (nom, email, mot_de_passe, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nom);
            stmt.setString(2, email);
            stmt.setString(3, motDePasse);
            stmt.setString(4, role);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authentifierUtilisateur(String email, String motDePasse) {
        String query = "SELECT * FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Retourne true si une ligne correspond
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
