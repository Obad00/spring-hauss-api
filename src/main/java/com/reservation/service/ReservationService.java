package com.reservation.service;

import com.reservation.entity.Reservation;
import com.reservation.enums.StatutReservation;
import com.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    // Créer une réservation
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
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
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Supprimer une réservation
    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    // Autres méthodes de service pour gérer les réservations...
}
