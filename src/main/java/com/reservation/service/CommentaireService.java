package com.reservation.service;

import com.reservation.entity.Commentaire;
import com.reservation.entity.Logement;
import com.reservation.entity.User;


import com.reservation.repository.CommentaireRepository;
import com.reservation.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private LogementService logementService;

     @Autowired
    private UserRepository userRepository;

    // Créer un nouveau commentaire et le lier à un logement
   // Méthode pour créer un commentaire et inclure les infos utilisateur
        public Commentaire creerCommentaire(Commentaire commentaire, Long logementId) {
            // Récupérer l'utilisateur authentifié à partir du SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName(); // Cela retourne l'email ou le username

            // Rechercher l'utilisateur à partir de son email ou username
            User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Assigner le nom complet et d'autres informations utilisateur au commentaire
            commentaire.setNom_complet(user.getNom()); // Assigner le nom complet depuis l'utilisateur
            commentaire.setUser(user); // Associer l'utilisateur au commentaire

            // Récupérer le Logement et lier le commentaire
            Logement logement = logementService.getLogementById(logementId);
            commentaire.setLogement(logement);

            // Sauvegarder le commentaire
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

    // Lire tous les commentaires liés à un logement spécifique
    public List<Commentaire> lireCommentairesParLogement(Long logementId) {
        return commentaireRepository.findByLogementId(logementId);
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


    public List<Commentaire> lireCommentairesParUtilisateurEmail(String email) {
    List<Logement> logements = logementService.getLogementsByUserEmail(email); // Récupérer les logements de l'utilisateur
    List<Commentaire> commentaires = new ArrayList<>();
    
    for (Logement logement : logements) {
        List<Commentaire> commentairesParLogement = commentaireRepository.findByLogementId(logement.getId());
        commentaires.addAll(commentairesParLogement); // Ajouter tous les commentaires à la liste
    }
    
    return commentaires; // Retourner la liste complète des commentaires
}
}
