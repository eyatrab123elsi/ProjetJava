package services;

import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UtilisateurService {

    private Connection conn;

    public UtilisateurService() {
        // Obtient la connexion à la base de données
        this.conn = DBConnection.getConnection();
    }

    // Méthode pour ajouter un utilisateur
    public boolean ajouterUtilisateur(String nom, String email, String motDePasse, String role) {
        String query = "INSERT INTO utilisateurs (nom, email, mot_de_passe, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nom);
            stmt.setString(2, email);
            stmt.setString(3, motDePasse); // Assure-toi de sécuriser le mot de passe avant de l'insérer (hashing)
            stmt.setString(4, role);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retourne true si l'insertion a réussi
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retourne false si une erreur se produit
        }
    }

    // Méthode pour mettre à jour le profil d'un utilisateur
    public boolean mettreAJourProfil(String nom, String email, String motDePasse) {
        String query = "UPDATE utilisateurs SET nom = ?, mot_de_passe = ? WHERE email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nom);          // Nouveau nom
            stmt.setString(2, motDePasse);   // Nouveau mot de passe
            stmt.setString(3, email);        // Email de l'utilisateur (critère de recherche)

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Retourner true si la mise à jour a réussi
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Retourner false en cas d'erreur
        }
    }
}
