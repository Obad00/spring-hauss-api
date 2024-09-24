package com.reservation.service;

import com.reservation.entity.Commentaire;
import com.reservation.repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    // Créer un nouveau commentaire
    public Commentaire creerCommentaire(Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
    }

    // Lire un commentaire par ID
    public Optional<Commentaire> lireCommentaire(Long id) {
        return commentaireRepository.findById(id);
    }

    // Lire tous les commentaires
    public List<Commentaire> lireTousLesCommentaires() {
        return commentaireRepository.findAll();
    }

    // Mettre à jour un commentaire
    public Commentaire mettreAJourCommentaire(Long id, Commentaire detailsCommentaire) {
        Commentaire commentaire = commentaireRepository.findById(id).orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));
        commentaire.setNom_complet(detailsCommentaire.getNom_complet());
        commentaire.setNote(detailsCommentaire.getNote());
        commentaire.setDescription(detailsCommentaire.getDescription());
        return commentaireRepository.save(commentaire);
    }

    // Supprimer un commentaire
    public void supprimerCommentaire(Long id) {
        commentaireRepository.deleteById(id);
    }
}
