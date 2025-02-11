package com.learnify.utilisateur.services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PasswordResetService {

    private final String username = "eyatrabelsi868@gmail.com"; // Adresse email de l'expéditeur
    private final String password = "uekl hjwd vzfv ydkh"; // Mot de passe d'application

    public void sendPasswordResetLink(String email, String token) {
        try {
            // Encodage du token pour éviter les erreurs dans l'URL
            String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8.toString());
            String resetLink = "http://localhost:8080/ResetPasswordPage?token=" + encodedToken;


            // Configuration des propriétés SMTP
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            // Création de la session mail
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Réinitialisation de mot de passe");

            // Contenu du message
            String messageContent = "Bonjour,\n\n"
                    + "Vous avez demandé la réinitialisation de votre mot de passe.\n"
                    + "Veuillez cliquer sur ce lien pour réinitialiser votre mot de passe :\n"
                    + resetLink + "\n\n"
                    + "Si vous n'êtes pas à l'origine de cette demande, ignorez cet e-mail.\n\n"
                    + "Cordialement,\n"
                    + "L'équipe Learnify";

            message.setText(messageContent);

            // Envoi de l'email
            Transport.send(message);
            System.out.println("Lien de réinitialisation envoyé à " + email);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'envoi de l'e-mail de réinitialisation.");
        }
    }
    private final EmailService emailService = new EmailService();

    public void sendPasswordResetCode(String email, String code) {
        String subject = "Code de réinitialisation";
        String body = "Votre code de réinitialisation est : " + code;
        emailService.sendEmail(email, subject, body);
    }

}
