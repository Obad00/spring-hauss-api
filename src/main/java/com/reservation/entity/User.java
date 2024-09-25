package com.reservation.entity;

import javax.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Reservation admin;

    @OneToMany(mappedBy = "user")
    private Set<Support> supports;

    @OneToMany(mappedBy = "user")
    private Set<Commentaire> commentaires;

    @OneToMany(mappedBy = "user")
    private Set<Notification> notifications;

    @Column(nullable = false)
    private boolean enabled; // Champ pour indiquer si l'utilisateur est activé

    // Méthode pour vérifier si l'utilisateur est activé
    public boolean isEnabled() {
        return enabled;
    }
}
