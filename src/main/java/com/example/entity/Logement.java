package com.example.entity;

import javax.persistence.*;
import com.example.enums.TypeLogement;
import com.example.enums.StatutLogement;
import java.util.Set;
import com.example.validator.ValidLogement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ValidLogement
@Entity
@Table(name = "logements")
public class Logement extends AbstractModel {

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeLogement type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutLogement statut;

    @Column
    private String image;

    @Column(nullable = false)
    private String ville;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String quartier;

    @Column(nullable = false)
    private Integer nombreChambre;

    @Column(nullable = false)
    private Integer nombreToilette;

    @Column
    private Integer nombreEtage;

    @Column(nullable = false)
    private Integer surface;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double prix;

    @ManyToOne
    @JoinColumn(name = "proprietaire_id")
    private Proprietaire proprietaire;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "logement")
    private Set<Reservation> reservations;
}
