package com.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reservation.entity.Logement;
import java.util.List;


@Repository
public interface LogementRepository extends JpaRepository<Logement, Long> {
    List<Logement> findByUserEmail(String email);  // Récupère tous les logements appartenant à l'utilisateur
}

