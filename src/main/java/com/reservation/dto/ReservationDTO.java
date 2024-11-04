package com.reservation.dto;

import com.reservation.entity.Logement;
import com.reservation.entity.User;
import java.time.Instant;

public class ReservationDTO {
    private Long id;                 
    private String statut;           
    private Instant createdAt;       
    private Logement logement;      
    private User user;              

    // Constructor with parameters in the desired order
    public ReservationDTO(Long id, String statut, Instant createdAt, Logement logement, User user) {
        this.id = id;                // Initialize ID
        this.statut = statut;        // Initialize status
        this.createdAt = createdAt;  // Initialize creation date
        this.logement = logement;    // Initialize associated logement
        this.user = user;            // Initialize user
    }

    // Getters
    public Long getId() {
        return id;                   // Get ID
    }

    public String getStatut() {
        return statut;               // Get status
    }

    public Instant getCreatedAt() {
        return createdAt;            // Get creation date
    }

    public Logement getLogement() {
        return logement;             // Get associated logement
    }

    public User getUser() {
        return user;                 // Get user who made the reservation
    }

    // Setters
    public void setId(Long id) {
        this.id = id;                // Set ID
    }

    public void setStatut(String statut) {
        this.statut = statut;        // Set status
    }

    public void setLogement(Logement logement) {
        this.logement = logement;    // Set associated logement
    }

    public void setUser(User user) {
        this.user = user;            // Set user
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;  // Set creation date
    }
}
