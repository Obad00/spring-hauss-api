package com.reservation.service;

import com.reservation.entity.Reservation;
import com.reservation.entity.User; // Assurez-vous d'importer votre entité User
import com.reservation.enums.StatutReservation;
import com.reservation.exception.UserNotFoundException;
import com.reservation.repository.ReservationRepository;
import com.reservation.repository.UserRepository;
import com.reservation.utils.JwtUtils; // Importez votre classe JwtUtils
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService; // Assurez-vous que ce service est bien défini

    @Autowired
    private JwtUtils jwtUtils; // Injection de JwtUtils

    // @Autowired
    // private EmailService emailService;

    // Créer une réservation
    // Créer une réservation
    public Reservation createReservation(Reservation reservation, String token) {
        // Extraire l'email de l'utilisateur à partir du token JWT
        String email = jwtUtils.getEmailFromToken(token); 
        System.out.println("Email extrait du token: " + email); // Log pour débogage
        
        // Trouver l'utilisateur par son email  
        User user = userService.findByEmail(email);
        if (user == null) {
            System.out.println("Aucun utilisateur trouvé avec l'email: " + email); // Log pour débogage
            throw new UserNotFoundException("Utilisateur non trouvé avec l'email : " + email);
        }
    
        // Associer l'utilisateur à la réservation
        reservation.setUser(user);
        
        // Enregistrer la réservation en base de données
        Reservation savedReservation = reservationRepository.save(reservation);

        // Envoyer l'email de confirmation de réservation
        // String subject = "Confirmation de réservation";
        // String message = "Bonjour " + user.getPrenom() + ",\n\n" + 
        //                  "Votre réservation pour le logement " + reservation.getLogement().getTitre() + 
        //                  " a été confirmée avec succès.";
        // emailService.sendReservationEmail(user.getEmail(), subject, message);

        return savedReservation;
    }
    

    // Modifier le statut de la réservation
    public Optional<Reservation> updateReservationStatus(Long reservationId, StatutReservation newStatus) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setStatut(newStatus);
            return Optional.of(reservationRepository.save(reservation));
        }
        return Optional.empty();
    }

    // Récupérer une réservation par son ID
    public Optional<Reservation> getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }

    // Récupérer toutes les réservations
    public List<Reservation> getReservationsByUserEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email); // Récupérer l'utilisateur par email
        if (optionalUser.isPresent()) {
            User user = optionalUser.get(); // Récupérer l'utilisateur
            return reservationRepository.findByUserId(user.getId()); // Récupérer les réservations de cet utilisateur
        }
        return Collections.emptyList(); // Aucun utilisateur trouvé
    }
    
    

    // Supprimer une réservation
    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    // Autres méthodes de service pour gérer les réservations...
}
