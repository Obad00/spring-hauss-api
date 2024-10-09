package com.reservation.controller;

import com.reservation.entity.Notification;
import com.reservation.entity.User;
import com.reservation.service.NotificationService;
import com.reservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    // Créer une notification
    @PostMapping
public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
    // Récupérer l'utilisateur authentifié
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    // Récupérer l'utilisateur depuis votre service
    User user = userService.findByEmail(email);
    notification.setUserId(user.getId()); // Associez l'ID de l'utilisateur ici


    return ResponseEntity.ok(notificationService.createNotification(notification));
}

    

}
