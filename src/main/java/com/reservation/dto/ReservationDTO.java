package com.reservation.dto;

import com.reservation.entity.Logement;
import com.reservation.entity.User;

public class ReservationDTO {
    private Long id;
    private String statut; // Ajouter le statut ici
    private Logement logement; // Supposons que vous ayez une classe Logement
    private User user; // Supposons que vous ayez une classe User

    // Constructeur
    public ReservationDTO(Long id, String statut, Logement logement, User user) {
        this.id = id;
        this.statut = statut; // Initialiser le statut
        this.logement = logement;
        this.user = user;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public String getStatut() {
        return statut;
    }

    public Logement getLogement() {
        return logement;
    }

    public User getUser() {
        return user;
    }
}
