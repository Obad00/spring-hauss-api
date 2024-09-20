package com.example.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "commentaires")
public class Commentaire extends AbstractModel {

    @Column(name = "nomComplet")
    private String nomComplet;

    @Column(nullable = false)
    private Integer note;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
}
