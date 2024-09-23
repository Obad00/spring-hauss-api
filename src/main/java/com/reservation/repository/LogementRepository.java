package com.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reservation.entity.Logement;

@Repository
public interface LogementRepository extends JpaRepository<Logement, Long> {
}
