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
@Table(name = "adresses")
public class Adresse extends AbstractModel {
    
    @Column(nullable = false)
    private String regions;

    @Column(nullable = false)
    private String localite;

    // Relation One-to-Many avec Logement
    @OneToMany(mappedBy = "adresse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Logement> logements;
}
