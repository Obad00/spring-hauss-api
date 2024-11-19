package com.reservation.controller;

import com.reservation.entity.Commentaire;
import com.reservation.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/commentaires")
public class CommentaireController {

    @Autowired
    private CommentaireService commentaireService;

    // Créer un nouveau commentaire pour un logement
    @PostMapping
    public ResponseEntity<Commentaire> creerCommentaire(@RequestBody Commentaire commentaire, @RequestParam Long logementId) {
        // Appel du service qui va récupérer les informations utilisateur du token et créer le commentaire
        return ResponseEntity.ok(commentaireService.creerCommentaire(commentaire, logementId));
    }
    

    // Lire un commentaire par ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Commentaire>> lireCommentaire(@PathVariable Long id) {
        return ResponseEntity.ok(commentaireService.lireCommentaire(id));
    }

    // Lire tous les commentaires
    @GetMapping
    public ResponseEntity<List<Commentaire>> lireTousLesCommentaires() {
        return ResponseEntity.ok(commentaireService.lireTousLesCommentaires());
    }

    // Lire tous les commentaires d'un logement spécifique
    @GetMapping("/logement/{logementId}")
    public ResponseEntity<List<Commentaire>> lireCommentairesParLogement(@PathVariable Long logementId) {
        return ResponseEntity.ok(commentaireService.lireCommentairesParLogement(logementId));
    }

    // Mettre à jour un commentaire
    @PutMapping("/{id}")
    public ResponseEntity<Commentaire> mettreAJourCommentaire(@PathVariable Long id, @RequestBody Commentaire detailsCommentaire) {
        return ResponseEntity.ok(commentaireService.mettreAJourCommentaire(id, detailsCommentaire));
    }

    // Supprimer un commentaire
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCommentaire(@PathVariable Long id) {
        commentaireService.supprimerCommentaire(id);
        return ResponseEntity.noContent().build();
        
    }

    @GetMapping("/utilisateur")
    public ResponseEntity<List<Commentaire>> lireCommentairesParUtilisateur(Authentication authentication) {
    String email = authentication.getName(); // Récupérer l'email de l'utilisateur connecté
    List<Commentaire> commentaires = commentaireService.lireCommentairesParUtilisateurEmail(email);
    return ResponseEntity.ok(commentaires);
}
}
