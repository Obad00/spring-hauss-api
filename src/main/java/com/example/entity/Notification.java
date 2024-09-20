package com.example.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "notifications")
public class Notification extends AbstractModel {

    @Column(nullable = false)
    private String sujet;

    @Column(nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
