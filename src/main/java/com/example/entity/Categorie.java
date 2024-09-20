package com.example.entity;

import javax.persistence.*;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "categories")
public class Categorie extends AbstractModel {

    @Column(nullable = false)
    private String nom;

    @OneToMany(mappedBy = "categorie")
    private Set<Logement> logements;

    // Getters and Setters
}
