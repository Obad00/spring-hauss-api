package com.reservation.service;

import com.reservation.entity.User;
import com.reservation.entity.Logement;
import com.reservation.repository.UserRepository;
import com.reservation.repository.LogementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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
    public Set<Logement> listerFavoris(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        return user.getLogementsFavoris(); // Retourne la liste des logements favoris
    }
}
