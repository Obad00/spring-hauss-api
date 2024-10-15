package com.reservation.repository;

import com.reservation.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {
    // Vous pouvez ajouter des méthodes de recherche personnalisées ici si nécessaire
}
