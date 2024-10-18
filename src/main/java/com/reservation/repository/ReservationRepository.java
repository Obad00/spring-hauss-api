package com.reservation.repository;

import com.reservation.entity.Logement;
import com.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
      // Trouver les réservations pour un utilisateur spécifique
      List<Reservation> findByUserId(Long userId); 
      List<Reservation> findByLogementIn(List<Logement> logements);  // Récupère les réservations pour une liste de logements
    }
