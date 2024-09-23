package com.reservation.entity;

import javax.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "locataires")
public class Locataire extends AbstractModel {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
