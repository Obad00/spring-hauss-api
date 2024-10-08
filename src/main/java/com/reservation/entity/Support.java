package com.reservation.entity;

import javax.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.reservation.enums.TypeDemande;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "supports")
public class Support extends AbstractModel {

    @Enumerated(EnumType.STRING)
    @Column(name = "type_demande")
    private TypeDemande type_demande;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    
}
