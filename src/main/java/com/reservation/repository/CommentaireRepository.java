package com.reservation.repository;

import com.reservation.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
}
