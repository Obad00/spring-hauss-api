package com.reservation.service;

import org.springframework.stereotype.Service;

import com.reservation.entity.Logement;
import com.reservation.exception.LogementNotFoundException;
import com.reservation.repository.LogementRepository;

import javax.validation.Valid;
import java.util.List;

@Service
public class LogementService {

    private final LogementRepository logementRepository;

    public LogementService(LogementRepository logementRepository) {
        this.logementRepository = logementRepository;
    }

    public Logement createLogement(@Valid Logement logement) {
        return logementRepository.save(logement);
    }

    public List<Logement> getAllLogements() {
        return logementRepository.findAll();
    }

    public Logement getLogementById(Long id) {
        return logementRepository.findById(id)
                .orElseThrow(() -> new LogementNotFoundException("Logement non trouvé"));
    }

    public Logement updateLogement(Long id, @Valid Logement logementDetails) {
        Logement logement = getLogementById(id);
        logement.setTitre(logementDetails.getTitre());
        logement.setAdresse(logementDetails.getAdresse());
        // Mettre à jour les autres champs
        return logementRepository.save(logement);
    }

    public void deleteLogement(Long id) {
        Logement logement = getLogementById(id);
        logementRepository.delete(logement);
    }
}
