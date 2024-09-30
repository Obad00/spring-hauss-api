package com.reservation.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.reservation.enums.StatutReservation;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "reservations")
public class Reservation extends AbstractModel {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutReservation statut;

    @ManyToOne
    @JoinColumn(name = "logement_id")
    private Logement logement;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Boolean deletedByOwner;

    @Column(nullable = false)
    private Boolean deletedByTenant;

    @Column(name = "archived_by_tenant_at")
    private LocalDateTime archivedByTenantAt;

    @Column(name = "archived_by_owner_at")
    private LocalDateTime archivedByOwnerAt;

    
}
