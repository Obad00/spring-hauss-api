package com.reservation.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "notifications")
public class Notification extends AbstractModel {

    @Column(nullable = false)
    private String sujet;

    @Column(nullable = false)
    private String message;

    // Définir la relation avec l'entité User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
