package com.example.entity;

import java.io.Serializable;
import java.time.Instant; // Import manquant pour Instant

import org.springframework.data.annotation.CreatedBy; // Import pour CreatedBy
import org.springframework.data.annotation.CreatedDate; // Import pour CreatedDate
import org.springframework.data.annotation.LastModifiedBy; // Import pour LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate; // Import pour LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractModel implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt; // Instant est maintenant reconnu

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt; // Instant est maintenant reconnu

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Integer createdBy; // CreatedBy est maintenant reconnu

    @LastModifiedBy
    @Column(name = "updated_by")
    private Integer updatedBy; // LastModifiedBy est maintenant reconnu

    @Column(name = "etat", columnDefinition = "INT DEFAULT 1")
    private Integer etat;
}
