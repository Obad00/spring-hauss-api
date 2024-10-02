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
        
        // Mettre à jour tous les champs avec les détails fournis
        logement.setTitre(logementDetails.getTitre());
        logement.setAdresse(logementDetails.getAdresse());
        logement.setType(logementDetails.getType());
        logement.setStatut(logementDetails.getStatut());
        logement.setImage(logementDetails.getImage());
        logement.setVille(logementDetails.getVille());
        logement.setRegion(logementDetails.getRegion());
        logement.setQuartier(logementDetails.getQuartier());
        logement.setNombre_chambre(logementDetails.getNombre_chambre());
        logement.setNombre_toilette(logementDetails.getNombre_toilette());
        logement.setNombre_etage(logementDetails.getNombre_etage());
        logement.setSurface(logementDetails.getSurface());
        logement.setDescription(logementDetails.getDescription());
        logement.setPrix(logementDetails.getPrix());
        
        // Sauvegarder les modifications
        return logementRepository.save(logement);
    }
    

    public void deleteLogement(Long id) {
        Logement logement = getLogementById(id);
        logementRepository.delete(logement);
    }
}
