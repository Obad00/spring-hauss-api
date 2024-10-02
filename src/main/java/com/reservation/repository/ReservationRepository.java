package com.reservation.repository;

import com.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Méthodes personnalisées si nécessaire...
}
