package com.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservation.entity.Adresse;
import com.reservation.entity.Images;
import com.reservation.entity.Logement;
import com.reservation.exception.LogementNotFoundException;
import com.reservation.repository.AdresseRepository;
import com.reservation.repository.ImagesRepository;
import com.reservation.repository.LogementRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
public class LogementService {

    private final LogementRepository logementRepository;

      @Autowired
    private AdresseRepository adresseRepository;

    @Autowired
    private ImagesRepository imagesRepository;


    public LogementService(LogementRepository logementRepository, AdresseRepository adresseRepository, ImagesRepository imagesRepository) {
        this.logementRepository = logementRepository;
        this.adresseRepository = adresseRepository;
    }
    @Transactional
    public Logement createLogement(@Valid Logement logement) {
    // Sauvegarder l'adresse si elle existe
    Adresse adresse = logement.getAdresse();
    if (adresse != null) {
        adresse = adresseRepository.save(adresse);
        logement.setAdresse(adresse);
    }

    // Sauvegarder le logement sans image
    Logement savedLogement = logementRepository.save(logement);
    System.out.println("Logement sauvegardé avec succès avec ID : " + savedLogement.getId());

    return savedLogement;
}

    
    @Transactional
    public void uploadImagesToLogement(Long id, List<String> url) {
        Logement logement = logementRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Logement non trouvé"));
        for (String imageUrl : url) {
            Images image = new Images();
            image.setUrl(imageUrl);
            image.setLogement(logement);
            imagesRepository.save(image);
        }
    }

    

    public List<Logement> getAllLogements() {
        return logementRepository.findAll();
    }

    public List<Logement> getLogementsByUserEmail(String email) {
        return logementRepository.findByUserEmail(email);
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
