package com.example.entity;

import javax.persistence.*;
import com.example.enums.TypeDemande;
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
    @Column(name = "typeDemande")
    private TypeDemande typeDemande;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    
}
