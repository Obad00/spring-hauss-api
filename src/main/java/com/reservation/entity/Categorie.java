package com.reservation.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "categories")
public class Categorie extends AbstractModel {

    @Column(nullable = false, unique = true)  // Ajout d'une contrainte d'unicité
    private String nom;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)  // Ajout du cascade pour gérer les opérations sur les logements
    private Set<Logement> logements;
}
