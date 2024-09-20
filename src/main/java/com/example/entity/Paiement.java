package com.example.entity;

import javax.persistence.*;
import com.example.enums.TypePaiement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "paiements")
public class Paiement extends AbstractModel {

    @Column(nullable = false)
    private Double montant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypePaiement type;

    @Column(nullable = false)
    private Double commission;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;


}
