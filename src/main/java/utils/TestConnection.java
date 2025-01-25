package Utils;

import utils.DBConnection;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        // Tester la connexion à la base de données via le Singleton
        DBConnection dbConnection = DBConnection.getInstance();
        Connection conn = dbConnection.getConn();

        if (conn != null) {
            System.out.println("Connexion à la base de données réussie !");
        } else {
            System.out.println("Échec de la connexion à la base de données.");
        }
    }
}
