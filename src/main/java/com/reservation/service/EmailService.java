package com.reservation.service;

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
}
