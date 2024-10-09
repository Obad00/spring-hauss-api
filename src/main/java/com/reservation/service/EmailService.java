package com.reservation.service;

import com.reservation.entity.User; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendReservationEmail(String to, String subject, String message) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);

            mailSender.send(mimeMessage);
            logger.info("Email envoyé avec succès à : " + to);
        } catch (MessagingException e) {
            logger.error("Erreur lors de l'envoi de l'email à : " + to, e);
            throw new RuntimeException("Erreur lors de l'envoi de l'email", e);
        }
    }

    public void sendEmail(String to, String subject, String message, User user) {
        // Créez un message personnalisé en ajoutant des informations sur l'utilisateur associé
        StringBuilder personalizedMessage = new StringBuilder();
        personalizedMessage.append("<html><body>");
        personalizedMessage.append("<h2>Bonjour,</h2>");
        personalizedMessage.append("<p>").append(message).append("</p>");
    
        // Ajoutez des informations sur l'utilisateur
        if (user != null) {
            personalizedMessage.append("<p><strong>Demande faite par :</strong> ").append(user.getNom()).append("</p>");
            personalizedMessage.append("<p><strong>Email de l'utilisateur :</strong> ").append(user.getEmail()).append("</p>");
        }
    
        personalizedMessage.append("<p>Cordialement,<br/>Votre équipe</p>");
        personalizedMessage.append("</body></html>");
    
        // Créez un MimeMessage
        MimeMessage mimeMessage = mailSender.createMimeMessage();
    
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(personalizedMessage.toString(), true); // true signifie que le texte est au format HTML
    
            // Utilisez JavaMailSender pour envoyer l'e-mail
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("Erreur lors de l'envoi de l'e-mail à : " + to, e);
            throw new RuntimeException("Erreur lors de l'envoi de l'e-mail", e);
        }
    }
        
    
}
