package com.reservation.entity;

import jakarta.persistence.*;
import com.reservation.enums.StatutLogement;
import com.reservation.enums.TypeLogement;
import com.reservation.validator.ValidLogement;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ValidLogement
@Table(name = "logements")
public class Logement extends AbstractModel {

    @Column(nullable = false)
    private String titre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeLogement type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutLogement statut;

    @Column(nullable = false)
    private Integer nombre_chambre;

    @Column(nullable = false)
    private Integer nombre_toilette;

    @Column
    private Integer nombre_etage;

    @Column(nullable = false)
    private Integer surface;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double prix;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "logement")
    private Set<Reservation> reservations;

    @ManyToMany(mappedBy = "logementsFavoris")
    private Set<User> utilisateursFavoris;

    @OneToMany(mappedBy = "logement")
    private Set<Commentaire> commentaires;

    @OneToMany(mappedBy = "logement", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Images> images = new HashSet<>();

    // Relation Many-to-One avec l'entité Adresse
    @ManyToOne
    @JoinColumn(name = "adresse_id", nullable = false)
    private Adresse adresse;
}
