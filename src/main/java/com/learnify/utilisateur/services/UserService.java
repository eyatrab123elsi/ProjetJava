package com.learnify.utilisateur.services;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    // Simule une base de données de tokens
    private static final Map<String, String> tokenDatabase = new HashMap<>();

    public boolean resetPassword(String token, String newPassword) {
        if (tokenDatabase.containsKey(token)) {
            String email = tokenDatabase.get(token);
            // Ici, on mettrait à jour le mot de passe de l'utilisateur
            System.out.println("Mot de passe mis à jour pour l'utilisateur : " + email);
            tokenDatabase.remove(token);  // Supprime le token après utilisation
            return true;
        }
        return false;
    }

    public void storeToken(String email, String token) {
        tokenDatabase.put(token, email);
    }
}
