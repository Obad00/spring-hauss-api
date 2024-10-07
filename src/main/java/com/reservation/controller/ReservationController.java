package com.reservation.controller;

import com.reservation.entity.Reservation;
import com.reservation.enums.StatutReservation; // Assurez-vous d'importer votre enum
import com.reservation.exception.UserNotFoundException;
import com.reservation.service.ReservationService;
import com.reservation.service.EmailService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.reservation.dto.ReservationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @Valid @RequestBody Reservation reservation) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UserNotFoundException("Utilisateur non trouvé. Veuillez vous connecter.");
        }
    
        // Récupérer le token de l'utilisateur depuis les headers
        String token = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Enlever "Bearer " pour obtenir le token brut
        }

        // Créer la réservation en passant le token
        Reservation createdReservation = reservationService.createReservation(reservation, token);

        // Récupérer l'email de l'utilisateur
        String userEmail = authentication.getName(); // Ou obtenez l'email depuis l'objet `User`
        
        // Envoyer l'email de confirmation
        String subject = "Confirmation de votre réservation";
        String body = "Bonjour, votre réservation a bien été effectuée. Détails de la réservation : ..."; // Personnalisez le contenu
        emailService.sendReservationEmail(userEmail, subject, body);

        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }
    


    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Reservation> updateReservationStatus(@PathVariable Long id, @RequestBody StatutReservation newStatus) {
    return reservationService.updateReservationStatus(id, newStatus)
            .map(updatedReservation -> {
                // Vérifiez si la réservation a un utilisateur et un email
                if (updatedReservation.getUser() != null && updatedReservation.getUser().getEmail() != null) {
                    String userEmail = updatedReservation.getUser().getEmail();
                    String subject = "Mise à jour de votre réservation";
                    String text = "Bonjour, le statut de votre réservation a été mis à jour en : " + newStatus;

                    // Envoi de l'email à l'utilisateur
                    try {
                        emailService.sendReservationEmail(userEmail, subject, text);
                    } catch (Exception e) {
                        // Log l'erreur mais continuez la mise à jour du statut
                        logger.error("Erreur lors de l'envoi de l'email à l'utilisateur", e);
                    }
                }

                return ResponseEntity.ok(updatedReservation);
            })
            .orElse(ResponseEntity.notFound().build());
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations(Authentication authentication) {
        String nom = authentication.getName();
        System.out.println("Utilisateur connecté : " + nom); // Log de l'utilisateur connecté
    
        List<Reservation> reservations = reservationService.getReservationsByUserEmail(nom);
        System.out.println("Réservations trouvées : " + reservations.size()); // Log du nombre de réservations
    
        List<ReservationDTO> reservationDTOs = reservations.stream()
            .map(reservation -> new ReservationDTO(
                reservation.getId(),
                reservation.getLogement(),
                reservation.getLogement().getUser() // Récupérer l'utilisateur du logement
            ))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(reservationDTOs);
    }
    
    

    
}
