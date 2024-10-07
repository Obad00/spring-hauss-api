package com.reservation.dto;

import com.reservation.entity.Logement;
import com.reservation.entity.User;

public class ReservationDTO {
    private Long id;
    private Logement logement;
    private User user;

    // Constructeur
    public ReservationDTO(Long id, Logement logement, User user) {
        this.id = id;
        this.logement = logement;
        this.user = user;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Logement getLogement() {
        return logement;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setLogement(Logement logement) {
        this.logement = logement;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
