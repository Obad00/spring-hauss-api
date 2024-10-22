package com.reservation.repository;

import com.reservation.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    // Récupérer tous les commentaires pour un logement spécifique
    List<Commentaire> findByLogementId(Long logementId);
}
