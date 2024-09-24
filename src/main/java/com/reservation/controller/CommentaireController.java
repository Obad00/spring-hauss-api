package com.reservation.controller;

import com.reservation.entity.Commentaire;
import com.reservation.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/commentaires")
public class CommentaireController {

    @Autowired
    private CommentaireService commentaireService;

    // Créer un nouveau commentaire
    @PostMapping
    public ResponseEntity<Commentaire> creerCommentaire(@RequestBody Commentaire commentaire) {
        return ResponseEntity.ok(commentaireService.creerCommentaire(commentaire));
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
}
