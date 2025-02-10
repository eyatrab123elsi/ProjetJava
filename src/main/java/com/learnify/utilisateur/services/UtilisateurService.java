package com.learnify.utilisateur.services;

import com.learnify.utilisateur.entities.Utilisateur;
import com.learnify.utilisateur.utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilisateurService {

    private Connection conn;

    public UtilisateurService() {
        // Initialisation de la connexion à la base de données
        this.conn = DBConnection.getInstance().getConn();
    }

    // Ajouter un utilisateur avec le statut de validation initial à FALSE
    public boolean ajouterUtilisateur(String nom, String prenom, String email, String motDePasse, String telephone, String adresse, String dateNaissance, String role) {
        String query = "INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, telephone, adresse, date_naissance, role, est_valide) VALUES (?, ?, ?, ?, ?, ?, ?, ?, FALSE)";
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

    // Vérifier si un email est déjà enregistré
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

    // Authentifier un utilisateur
    public String authentifierUtilisateur(String email, String motDePasse) {
        // Vérification spécifique pour l'admin (hors base de données)
        if ("admin@gmail.com".equals(email) && "admin".equals(motDePasse)) {
            return "Admin";
        }

        String query = "SELECT role, est_valide FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                boolean estValide = rs.getBoolean("est_valide");
                if (!estValide) {
                    return "NonValidé";
                }
                return rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Récupérer les utilisateurs par rôle
    public List<Utilisateur> getUtilisateursByRole(String role) {
        String query = "SELECT * FROM utilisateurs WHERE role = ?";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String adresse = rs.getString("adresse");
                LocalDate dateNaissance = rs.getDate("date_naissance").toLocalDate();
                boolean estValide = rs.getBoolean("est_valide");
                utilisateurs.add(new Utilisateur(id, nom, prenom, email, telephone, adresse, dateNaissance, "", role, estValide));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    // Supprimer un utilisateur par email
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

    public boolean mettreAJourUtilisateur(Utilisateur user) {
        String query = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ?, telephone = ?, adresse = ?, date_naissance = ?, role = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getPrenom());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getTelephone());
            stmt.setString(5, user.getAdresse());
            stmt.setDate(6, Date.valueOf(user.getDateNaissance()));
            stmt.setString(7, user.getRole());
            stmt.setInt(8, user.getId()); // Utilisation de l'ID pour la mise à jour

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Rejeter un utilisateur (suppression)
    public boolean rejeterUtilisateur(String email) {
        return supprimerUtilisateur(email); // Utilise la méthode existante
    }

    public boolean validerUtilisateur(String email) {
        String query = "UPDATE utilisateurs SET est_valide = TRUE WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Si l'utilisateur est validé avec succès, envoyer l'email
                EmailService emailService = new EmailService();
                String subject = "Votre compte a été validé";
                String body = "Bonjour,\n\nVotre compte sur Learnify a été validé avec succès. Vous pouvez maintenant vous connecter.\n\nCordialement,\nL'équipe Learnify";
                emailService.sendEmail(email, subject, body);
                return true;
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet email.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    // Récupérer un utilisateur par email
    public Utilisateur getUtilisateurByEmail(String email) {
        String query = "SELECT * FROM utilisateurs WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String telephone = rs.getString("telephone");
                String adresse = rs.getString("adresse");
                String role = rs.getString("role");
                LocalDate dateNaissance = rs.getDate("date_naissance").toLocalDate();
                boolean estValide = rs.getBoolean("est_valide");
                return new Utilisateur(id, nom, prenom, email, telephone, adresse, dateNaissance, "", role, estValide);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Vérifier si un email existe
    public boolean emailExiste(String email) {
        String query = "SELECT COUNT(*) FROM utilisateurs WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean Modify(Utilisateur utilisateur, String ancienEmail) {
        String query = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ?, telephone = ?, adresse = ?, role = ?, date_naissance = ? WHERE email = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getPrenom());
            preparedStatement.setString(3, utilisateur.getEmail()); // Nouvel email
            preparedStatement.setString(4, utilisateur.getTelephone());
            preparedStatement.setString(5, utilisateur.getAdresse());
            preparedStatement.setString(6, utilisateur.getRole());
            preparedStatement.setDate(7, java.sql.Date.valueOf(utilisateur.getDateNaissance()));
            preparedStatement.setString(8, ancienEmail); // Ancien email pour la clause WHERE

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Récupérer les utilisateurs non validés
    public List<Utilisateur> getUtilisateursNonValides() {
        String query = "SELECT * FROM utilisateurs WHERE est_valide = FALSE";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String adresse = rs.getString("adresse");
                String role = rs.getString("role");
                LocalDate dateNaissance = rs.getDate("date_naissance").toLocalDate();
                utilisateurs.add(new Utilisateur(id, nom, prenom, email, telephone, adresse, dateNaissance, "", role, false));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }
    // Simule une base de données de codes de réinitialisation
    private static final Map<String, String> resetCodeDatabase = new HashMap<>();

    // Stocker le code dans la base de données
    public void storeResetCode(String email, String code) {
        resetCodeDatabase.put(email, code);
    }

    // Valider le code de réinitialisation
    public boolean validateResetCode(String email, String code) {
        return resetCodeDatabase.containsKey(email) && resetCodeDatabase.get(email).equals(code);
    }

    public boolean resetPassword(String email, String newPassword) {
        String query = "UPDATE utilisateurs SET mot_de_passe = ? WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword); // Nouveau mot de passe
            stmt.setString(2, email); // Email de l'utilisateur

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retourne true si la mise à jour a réussi
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retourne false en cas d'erreur
        }
    }
}