package com.learnify.utilisateur.services;

import com.learnify.utilisateur.entities.Utilisateur;
import com.learnify.utilisateur.utils.DBConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurService {

    private Connection conn;

    public UtilisateurService() {
        // Utiliser getConn() au lieu de getConnection()
        this.conn = DBConnection.getInstance().getConn();
    }
    private Connection connection;



    public boolean ajouterUtilisateur(String nom, String prenom, String email, String motDePasse, String telephone, String adresse, String dateNaissance, String role) {
        // Le statut de validation initialement sur false (en attente de validation)
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
            } else {
                System.out.println("Utilisateur non trouvé dans la base de données.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Utilisateur> getUtilisateursByRole(String role) {
        String query = "SELECT * FROM utilisateurs WHERE role = ?";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");  // Récupération de 'prenom'
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String adresse = rs.getString("adresse");
                LocalDate dateNaissance = rs.getDate("date_naissance").toLocalDate(); // Assurez-vous que la colonne est de type DATE
                utilisateurs.add(new Utilisateur(nom, prenom, email, telephone, adresse, dateNaissance, "", role));
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
    public boolean rejeterUtilisateur(String email) {
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




    public Utilisateur getUtilisateurByEmail(String email) {
        String query = "SELECT * FROM utilisateurs WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String telephone = rs.getString("telephone");
                String adresse = rs.getString("adresse");
                String role = rs.getString("role");
                String dateNaissanceString = rs.getString("date_naissance");

                LocalDate dateNaissance = null;
                if (dateNaissanceString != null && !dateNaissanceString.isEmpty()) {
                    dateNaissance = LocalDate.parse(dateNaissanceString, DateTimeFormatter.ISO_DATE);
                }

                return new Utilisateur(nom, prenom, email, telephone, adresse, dateNaissance, "", role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si l'utilisateur n'est pas trouvé
    }
    public boolean emailExiste(String email) {
        String query = "SELECT COUNT(*) FROM utilisateurs WHERE email = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Utiliser 'conn' ici
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Si le nombre d'occurrences est supérieur à 0, l'email existe
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

    public boolean mettreAJourUtilisateur(Utilisateur user) {
        String query = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ?, telephone = ?, adresse = ?, date_naissance = ?, role = ? WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getPrenom());
            stmt.setString(3, user.getEmail());

            stmt.setString(4, user.getTelephone());
            stmt.setString(5, user.getAdresse());
            stmt.setString(6, user.getDateNaissance().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            stmt.setString(7, user.getRole());
            stmt.setString(8, user.getEmail());  // Mise à jour basée sur l'email actuel

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }





    public List<Utilisateur> getUtilisateursNonValides() {
        String query = "SELECT * FROM utilisateurs WHERE est_valide = FALSE";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String adresse = rs.getString("adresse");
                String role = rs.getString("role");
                LocalDate dateNaissance = rs.getDate("date_naissance").toLocalDate();
                utilisateurs.add(new Utilisateur(nom, prenom, email, telephone, adresse, dateNaissance, "", role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }
}