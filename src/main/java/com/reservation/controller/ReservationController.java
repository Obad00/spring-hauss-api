package com.reservation.controller;

import com.reservation.entity.Logement;
import com.reservation.entity.Reservation;
import com.reservation.entity.User;
import com.reservation.enums.StatutReservation; // Assurez-vous d'importer votre enum
import com.reservation.exception.UserNotFoundException;
import com.reservation.service.ReservationService;
import com.reservation.service.EmailService;
import com.reservation.service.LogementService;

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
import java.util.Set;
import java.util.HashSet;
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

    @Autowired
    private LogementService logementService; 

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservation) {
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
        
        // Détails du logement
        String logementDetails = "Nom du logement : " + createdReservation.getLogement().getTitre() + "<br>" +
                                 "Adresse : " + createdReservation.getLogement().getAdresse() + "<br>";
    
        // Préparer l'email de confirmation pour le locataire
        String subjectForUser = "Confirmation de votre réservation";
        String bodyForUser = "<html>" +
                "<body>" +
                "<h2>Bonjour,</h2>" +
                "<p>Votre réservation a bien été effectuée avec succès.</p>" +
                "<h3>Détails de la réservation :</h3>" +
                "<ul>" +
                "<li>" + logementDetails + "</li>" +
                "</ul>" +
                "<p>Merci de votre confiance !</p>" +
                "<p>Cordialement,<br>L'équipe de réservation.</p>" +
                "</body>" +
                "</html>";
    
        // Envoyer l'email de confirmation au locataire
        emailService.sendReservationEmail(userEmail, subjectForUser, bodyForUser);
    
        // Récupérer le propriétaire associé au logement
        User proprietaire = createdReservation.getLogement().getUser();
        if (proprietaire != null) {
            // Préparer l'email pour le propriétaire
            String ownerEmail = proprietaire.getEmail();
            String subjectForOwner = "Nouvelle réservation pour votre logement";
            String bodyForOwner = "<html>" +
                    "<body>" +
                    "<h2>Bonjour " + proprietaire.getNom() + ",</h2>" +
                    "<p>Vous avez une nouvelle réservation pour votre logement.</p>" +
                    "<h3>Détails de la réservation :</h3>" +
                    "<ul>" +
                    "<li>" + logementDetails + "</li>" +
                    "<li><strong>Nom du locataire :</strong> " + authentication.getName() + "</li>" +
                    "</ul>" +
                    "<p>Merci de votre attention !</p>" +
                    "<p>Cordialement,<br>L'équipe de réservation.</p>" +
                    "</body>" +
                    "</html>";
    
            // Envoyer l'email de notification au propriétaire
            emailService.sendReservationEmail(ownerEmail, subjectForOwner, bodyForOwner);
        }
    
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
    
                        // Détails du logement et du propriétaire
                        String logementDetails = "Nom du logement : " + updatedReservation.getLogement().getTitre() + "<br>" +
                                                 "Adresse : " + updatedReservation.getLogement().getAdresse() + "<br>" +
                                                 "Propriétaire : " + updatedReservation.getLogement().getUser().getNom() + " (" +
                                                 updatedReservation.getLogement().getUser().getEmail() + ")"; // Supposant que l'email est aussi récupéré
    
                        String text = "<html>" +
                                "<head>" +
                                "<style>" +
                                "    body { font-family: Arial, sans-serif; background-color: #f7f7f7; padding: 20px; }" +
                                "    .container { max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }" +
                                "    h2 { color: #356F37; }" +
                                "    p { font-size: 16px; line-height: 1.5; }" +
                                "    .footer { margin-top: 20px; font-size: 12px; color: #777; }" +
                                "</style>" +
                                "</head>" +
                                "<body>" +
                                "<div class='container'>" +
                                "<h2>Bonjour,</h2>" +
                                "<p>Le statut de votre réservation a été mis à jour en : <strong>" + newStatus + "</strong>.</p>" +
                                "<p>Voici les détails de votre logement :</p>" +
                                "<p>" + logementDetails + "</p>" +
                                "<p>Merci de votre confiance !</p>" +
                                "<p>Cordialement,<br>L'équipe de réservation.</p>" +
                                "</div>" +
                                "<div class='footer'>Cet email a été généré automatiquement. Veuillez ne pas répondre.</div>" +
                                "</body>" +
                                "</html>";
    
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
public ResponseEntity<List<ReservationDTO>> getAllReservationsForOwner(Authentication authentication) {
    // Récupérer le nom ou email de l'utilisateur connecté
    String nom = authentication.getName();
    System.out.println("Utilisateur connecté (propriétaire) : " + nom); // Log de l'utilisateur connecté
    
    // Récupérer les logements possédés par cet utilisateur
    List<Logement> logements = logementService.getLogementsByUserEmail(nom);
    System.out.println("Logements trouvés : " + logements.size()); // Log du nombre de logements trouvés
    
    // Récupérer les réservations liées à ces logements
    List<Reservation> reservationsForOwnedLogements = reservationService.getReservationsByLogements(logements);
    System.out.println("Réservations trouvées pour les logements : " + reservationsForOwnedLogements.size()); // Log du nombre de réservations

    // Récupérer les réservations faites par cet utilisateur
    List<Reservation> reservationsByUser = reservationService.getReservationsByUserEmail(nom);
    System.out.println("Réservations faites par l'utilisateur : " + reservationsByUser.size()); // Log du nombre de réservations faites par l'utilisateur

    // Utiliser un Set pour éviter les doublons
    Set<Reservation> combinedReservations = new HashSet<>();
    combinedReservations.addAll(reservationsForOwnedLogements); // Ajouter les réservations liées aux logements
    combinedReservations.addAll(reservationsByUser); // Ajouter les réservations faites par l'utilisateur

    // Transformer les réservations combinées en DTO
    List<ReservationDTO> reservationDTOs = combinedReservations.stream()
        .map(reservation -> new ReservationDTO(
            reservation.getId(),
            reservation.getStatut().toString(), // Convertir en String si c'est une énumération
            reservation.getLogement(),
            reservation.getLogement().getUser() // Récupérer l'utilisateur du logement
        ))
        .collect(Collectors.toList());

    return ResponseEntity.ok(reservationDTOs);
}


    
    
    
    

    
}
