package com.reservation.entity;

import jakarta.persistence.*;
import com.reservation.enums.RoleUtilisateur;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "users")
public class User extends AbstractModel {

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String telephone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleUtilisateur role;

    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations;

    @OneToMany(mappedBy = "user")
    private Set<Support> supports;

    @OneToMany(mappedBy = "user")
    private Set<Commentaire> commentaires;

    @OneToMany(mappedBy = "user")
    private Set<Notification> notifications;

    @Column(nullable = false)
    private boolean enabled;

    // Méthode pour vérifier si l'utilisateur est activé
    public boolean isEnabled() {
        return enabled;
    }

    // Relation Many-to-Many pour les favoris
    @ManyToMany
    @JoinTable(
        name = "favoris",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "logement_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Logement> logementsFavoris;
}
