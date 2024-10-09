package com.reservation.repository;

import com.reservation.entity.User;
import com.reservation.enums.RoleUtilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
   // Méthode pour récupérer les utilisateurs par rôle
    List<User> findByRole(RoleUtilisateur role);
    
    // Méthode pour récupérer les utilisateurs par rôle et par nom
    List<User> findByNomContaining(String nom);
    List<User> findByPrenomContaining(String prenom);

    
    
}


