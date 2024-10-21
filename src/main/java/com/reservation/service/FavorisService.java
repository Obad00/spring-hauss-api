package com.reservation.service;

import com.reservation.entity.User;
import com.reservation.dto.LogementFavoriDTO;
import com.reservation.dto.LogementProprietaireDTO;
import com.reservation.entity.Logement;
import com.reservation.repository.UserRepository;
import com.reservation.repository.LogementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@Service
public class FavorisService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogementRepository logementRepository;

    // Ajouter un logement aux favoris de l'utilisateur authentifié
    public void ajouterFavori(String email, Long logementId) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        Logement logement = logementRepository.findById(logementId)
            .orElseThrow(() -> new IllegalArgumentException("Logement non trouvé"));

        user.getLogementsFavoris().add(logement); // Ajouter le logement aux favoris
        userRepository.save(user); // Sauvegarder l'utilisateur avec ses favoris mis à jour
    }

    // Retirer un logement des favoris de l'utilisateur authentifié
    public void retirerFavori(String email, Long logementId) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        Logement logement = logementRepository.findById(logementId)
            .orElseThrow(() -> new IllegalArgumentException("Logement non trouvé"));

        user.getLogementsFavoris().remove(logement); // Retirer le logement des favoris
        userRepository.save(user); // Sauvegarder l'utilisateur avec ses favoris mis à jour
    }

    // Lister les logements favoris de l'utilisateur authentifié
    public Set<LogementFavoriDTO> listerFavoris(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
    
        // Mapper les logements favoris en DTO
        return user.getLogementsFavoris().stream()
            .map(logement -> new LogementFavoriDTO(
                logement.getId(),
                logement.getTitre(),
                logement.getPrix(),
                logement.getDescription(),
                logement.getNombre_chambre(),
                logement.getNombre_toilette(),
                logement.getAdresse() != null ? logement.getAdresse().getRegions() : "Régions non disponibles",
                logement.getAdresse() != null ? logement.getAdresse().getLocalite() : "Localité non disponible",
                logement.getCategorie() != null ? logement.getCategorie().getNom() : "Catégorie non disponible"
            ))
            .collect(Collectors.toSet());
    }


    public List<LogementProprietaireDTO> obtenirLogementsFavorisPourProprietaire(String emailProprietaire) {
    // Récupérer tous les logements créés par le propriétaire
    List<Logement> logements = logementRepository.findByUserEmail(emailProprietaire);

    // Mapper chaque logement en DTO avec le nombre d'utilisateurs ayant mis le logement en favoris
    return logements.stream().map(logement -> 
    LogementProprietaireDTO.builder()
        .id(logement.getId())
        .titre(logement.getTitre())
        .prix(logement.getPrix())
        .description(logement.getDescription())
        .nombre_chambre(logement.getNombre_chambre())
        .nombre_toilette(logement.getNombre_toilette())
        .adresseRegion(logement.getAdresse() != null ? logement.getAdresse().getRegions() : "Régions non disponibles")
        .adresseLocalite(logement.getAdresse() != null ? logement.getAdresse().getLocalite() : "Localité non disponible")
        .categorie(logement.getCategorie() != null ? logement.getCategorie().getNom() : "Catégorie non disponible")
        .nombreUtilisateursFavoris(logement.getUtilisateursFavoris().size())
        .build()
).collect(Collectors.toList());

}

    
    
    
}
