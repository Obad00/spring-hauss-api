package com.example.repository;

import com.example.entity.Logement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogementRepository extends JpaRepository<Logement, Long> {
}
