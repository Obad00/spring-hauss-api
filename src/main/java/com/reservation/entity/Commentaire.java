package com.reservation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "commentaires")
public class Commentaire extends AbstractModel {

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nom_complet", nullable = false)
    private String nom_complet;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer note;

    @NotNull
    @Size(min = 10, max = 1000)
    @Column(nullable = false)
    private String description;

    // Relation Many-to-One vers User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relation Many-to-One vers Logement
    @ManyToOne
    @JoinColumn(name = "logement_id", nullable = false)
    private Logement logement;
}
