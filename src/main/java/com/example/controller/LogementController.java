package com.example.controller;

import com.example.entity.Logement;
import com.example.service.LogementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/logements")
public class LogementController {

    private final LogementService logementService;

    public LogementController(LogementService logementService) {
        this.logementService = logementService;
    }

    @PostMapping
    public ResponseEntity<Logement> createLogement(@Valid @RequestBody Logement logement) {
        Logement createdLogement = logementService.createLogement(logement);
        return new ResponseEntity<>(createdLogement, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Logement>> getAllLogements() {
        List<Logement> logements = logementService.getAllLogements();
        return ResponseEntity.ok(logements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Logement> getLogementById(@PathVariable Long id) {
        Logement logement = logementService.getLogementById(id);
        return ResponseEntity.ok(logement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Logement> updateLogement(@PathVariable Long id, @Valid @RequestBody Logement logementDetails) {
        Logement updatedLogement = logementService.updateLogement(id, logementDetails);
        return ResponseEntity.ok(updatedLogement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogement(@PathVariable Long id) {
        logementService.deleteLogement(id);
        return ResponseEntity.noContent().build();
    }
}
