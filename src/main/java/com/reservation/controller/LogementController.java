package com.reservation.controller;

import com.reservation.entity.User; // Importez votre entité User
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.reservation.service.UserService; // Assurez-vous d'importer votre UserService
import com.reservation.utils.JwtUtils; // Importez votre classe JwtUtils
import org.springframework.security.core.Authentication;

import com.reservation.entity.Logement;
import com.reservation.exception.UserNotFoundException;
import com.reservation.service.LogementService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/logements")
public class LogementController {

    private final LogementService logementService;

    @Autowired
    private UserService userService; // Injection du UserService

    @Autowired
    private JwtUtils jwtUtils; // Injection de JwtUtils

    public LogementController(LogementService logementService) {
        this.logementService = logementService;
    }

      @PostMapping
    public ResponseEntity<Logement> createLogement(@Valid @RequestBody Logement logement) {
        
        // Récupérer le token de l'utilisateur depuis les headers
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UserNotFoundException("Utilisateur non trouvé. Veuillez vous connecter.");
        }

        // Récupérer le token à partir des headers
        String token = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getHeader("Authorization");

        // Vérifiez que le token est au format correct
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Enlever "Bearer " pour obtenir le token brut
        }

        // Extraire l'email de l'utilisateur à partir du token JWT
        String email = jwtUtils.getEmailFromToken(token); 
        
        // Trouver l'utilisateur par son email
        User user = userService.findByEmail(email);
        
        // Vérifiez si l'utilisateur existe
        if (user == null) {
            throw new UserNotFoundException("Utilisateur non trouvé avec l'email : " + email);
        }

        // Associer l'utilisateur au logement
        logement.setUser(user); // Assurez-vous que Logement a un champ pour User

        // Créer le logement
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
