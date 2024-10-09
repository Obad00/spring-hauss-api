package com.reservation.entity;

import javax.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    @Column(name = "user_id") // Nom de la colonne dans la base de données
    private Long userId; // Utilisez ce champ pour stocker l'ID de l'utilisateur

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId; // Notez que nous utilisons userId ici
    }

}
