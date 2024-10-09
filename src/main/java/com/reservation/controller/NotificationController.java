package com.reservation.controller;

import com.reservation.entity.Notification;
import com.reservation.entity.User;
import com.reservation.enums.RoleUtilisateur;
import com.reservation.service.EmailService;
import com.reservation.service.NotificationService;
import com.reservation.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    // Créer une notification
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        // Récupérer l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
    
        // Récupérer l'utilisateur depuis votre service
        User user = userService.findByEmail(email);
        notification.setUserId(user.getId()); // Associez l'ID de l'utilisateur ici
    
        // Créer la notification
        Notification savedNotification = notificationService.createNotification(notification);
    
        // Récupérer tous les utilisateurs ayant le rôle PROPRIETAIRE
        List<User> proprietaires = userService.findByRole(RoleUtilisateur.PROPRIETAIRE);
    
        // Envoyer une notification et un e-mail à chaque PROPRIETAIRE
        for (User proprietaire : proprietaires) {
            // Associer la notification à chaque propriétaire
            Notification proprietaireNotification = new Notification();
            proprietaireNotification.setSujet(notification.getSujet());
            proprietaireNotification.setMessage(notification.getMessage());
            proprietaireNotification.setUserId(proprietaire.getId());
    
            // Enregistrer la notification
            notificationService.createNotification(proprietaireNotification);
    
            // Envoyer un e-mail avec l'utilisateur associé
            emailService.sendEmail(proprietaire.getEmail(), "Nouvelle notification", notification.getMessage(), user);
        }
    
        return ResponseEntity.ok(savedNotification);
    }
    
    

}
