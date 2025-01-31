package com.learnify.utilisateur.services;

import com.learnify.utilisateur.entities.Utilisateur;
import com.learnify.utilisateur.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurService {

    private Connection conn;

    public UtilisateurService() {
        // Utiliser getConn() au lieu de getConnection()
        this.conn = DBConnection.getInstance().getConn();
    }

    // Méthode pour ajouter un utilisateur
    public boolean ajouterUtilisateur(String nom, String prenom, String email, String motDePasse, String telephone, String adresse, String dateNaissance, String role) {
        String query = "INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, telephone, adresse, date_naissance, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, email);
            stmt.setString(4, motDePasse);
            stmt.setString(5, telephone);
            stmt.setString(6, adresse);
            stmt.setString(7, dateNaissance);
            stmt.setString(8, role);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour vérifier si un email est déjà enregistré
    public boolean isEmailRegistered(String email) {
        String query = "SELECT * FROM utilisateurs WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Retourne true si l'email existe
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String authentifierUtilisateur(String email, String motDePasse) {
        // Vérification spécifique pour l'admin (hors base de données)
        if ("admin@gmail.com".equals(email) && "admin".equals(motDePasse)) {
            System.out.println("Admin détecté, connexion réussie.");
            return "Admin";
        }

        // Vérification dans la base de données avec le champ "est_valide"
        String query = "SELECT role, est_valide FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                boolean estValide = rs.getBoolean("est_valide");
                if (!estValide) {
                    return "NonValidé"; // Indique que le compte n'est pas encore validé
                }
                return rs.getString("role");  // Retourne le rôle si validé
            } else {
                System.out.println("Utilisateur non trouvé dans la base de données.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour récupérer les utilisateurs par rôle
    public List<Utilisateur> getUtilisateursByRole(String role) {
        String query = "SELECT * FROM utilisateurs WHERE role = ?";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                utilisateurs.add(new Utilisateur(nom, email, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    public boolean supprimerUtilisateur(String email) {
        String query = "DELETE FROM utilisateurs WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean mettreAJourUtilisateur(String email, String nouveauNom, String nouveauEmail) {
        String query = "UPDATE utilisateurs SET nom = ?, email = ? WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nouveauNom);
            stmt.setString(2, nouveauEmail);
            stmt.setString(3, email);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour valider un utilisateur
    public boolean validerUtilisateur(String email) {
        String query = "UPDATE utilisateurs SET est_valide = TRUE WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour récupérer les utilisateurs en attente de validation
    public List<Utilisateur> getUtilisateursNonValides() {
        String query = "SELECT * FROM utilisateurs WHERE est_valide = FALSE";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String role = rs.getString("role");
                utilisateurs.add(new Utilisateur(nom, email, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }
}