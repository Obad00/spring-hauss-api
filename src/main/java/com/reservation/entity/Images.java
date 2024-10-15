package com.reservation.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "images")
public class Images extends AbstractModel {

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "logement_id", nullable = false)
    private Logement logement;
}
