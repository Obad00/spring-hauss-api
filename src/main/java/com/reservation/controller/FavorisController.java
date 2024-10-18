package com.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.reservation.dto.LogementFavoriDTO;
import com.reservation.service.FavorisService;

import java.util.Set;

@RestController
@RequestMapping("/api/favoris")
public class FavorisController {

    @Autowired
    private FavorisService favorisService;

    @PostMapping("/ajouter")
    public ResponseEntity<String> ajouterFavori(@RequestParam Long logementId) {
        // Récupérer l'utilisateur authentifié
        String email = getAuthenticatedEmail();
        
        // Appeler le service pour ajouter le favori
        favorisService.ajouterFavori(email, logementId);

        return ResponseEntity.ok("Logement ajouté aux favoris !");
    }

    @DeleteMapping("/retirer")
    public ResponseEntity<String> retirerFavori(@RequestParam Long logementId) {
        // Récupérer l'utilisateur authentifié
        String email = getAuthenticatedEmail();
        
        // Appeler le service pour retirer le favori
        favorisService.retirerFavori(email, logementId);

        return ResponseEntity.ok("Logement retiré des favoris !");
    }

    // Lister les logements favoris
@GetMapping
public ResponseEntity<Set<LogementFavoriDTO>> listerFavoris() {
    String email = getAuthenticatedEmail(); // Méthode pour récupérer l'email de l'utilisateur authentifié
    Set<LogementFavoriDTO> logementsFavoris = favorisService.listerFavoris(email);
    return ResponseEntity.ok(logementsFavoris); // Retourner les logements favoris
}


    // Méthode pour obtenir l'email de l'utilisateur authentifié
    private String getAuthenticatedEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername(); // Si email est utilisé comme nom d'utilisateur
        } else {
            return principal.toString();
        }
    }
}
