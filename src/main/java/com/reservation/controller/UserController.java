package com.reservation.controller;

import com.reservation.dto.UserRegistrationDTO;
import com.reservation.entity.User;
import com.reservation.repository.UserRepository;
import com.reservation.enums.RoleUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users") // Préfixe pour toutes les routes utilisateur
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Méthode pour enregistrer un utilisateur
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Erreur de validation : " +
                bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(", ")));
        }

        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Cet email est déjà utilisé !");
        }

        User user = new User();
        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setAdresse(userDTO.getAdresse());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setTelephone(userDTO.getTelephone());
        user.setRole(RoleUtilisateur.valueOf(userDTO.getRole()));
        user.setEnabled(true);

        userRepository.save(user);
        return ResponseEntity.ok("Utilisateur enregistré avec succès !");
    }

    // Méthode pour mettre à jour un utilisateur existant
   @PutMapping("/update/{id}")
public ResponseEntity<String> updateUser(@PathVariable Long id, 
                                         @Valid @RequestBody UserRegistrationDTO userDTO, 
                                         BindingResult bindingResult) {
    try {
        // Vérifier les erreurs de validation
        if (bindingResult.hasErrors()) {
            String validationErrors = bindingResult.getAllErrors().stream()
                                        .map(error -> error.getDefaultMessage())
                                        .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Erreur de validation : " + validationErrors);
        }

        // Vérifier si l'utilisateur existe
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé avec l'ID : " + id);
        }

        // Récupérer l'utilisateur existant
        User user = optionalUser.get();

        // Vérifier si l'email existe déjà pour un autre utilisateur
        Optional<User> existingUserWithEmail = userRepository.findByEmail(userDTO.getEmail());
        if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("L'email " + userDTO.getEmail() + " est déjà utilisé par un autre utilisateur.");
        }

        // Mettre à jour les attributs
        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setAdresse(userDTO.getAdresse());
        user.setEmail(userDTO.getEmail());

        // Encodage conditionnel du mot de passe
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        user.setTelephone(userDTO.getTelephone());

        // Mettre à jour le rôle (vérifier que le rôle est valide avant)
        try {
            user.setRole(RoleUtilisateur.valueOf(userDTO.getRole()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Rôle invalide : " + userDTO.getRole());
        }

        // Sauvegarder les modifications
        userRepository.save(user);

        return ResponseEntity.ok("Utilisateur mis à jour avec succès !");
        
    } catch (DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().body("Erreur de mise à jour : " + e.getMessage());
    } catch (Exception e) {
        // Capturer toute exception et renvoyer un message personnalisé
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur : " + e.getMessage());
    }
}

    
    // Méthode pour supprimer un utilisateur
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé avec l'ID : " + id);
        }

        userRepository.deleteById(id);
        return ResponseEntity.ok("Utilisateur supprimé avec succès !");
    }

    // Méthode pour récupérer les informations d'un utilisateur par son ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalUser.get());
    }

    // Méthode pour lister tous les utilisateurs
    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/block/{id}")
public ResponseEntity<String> blockUser(@PathVariable Long id) {
    // Vérifier si l'utilisateur existe
    Optional<User> optionalUser = userRepository.findById(id);
    if (!optionalUser.isPresent()) {
        return ResponseEntity.badRequest().body("Utilisateur non trouvé avec l'ID : " + id);
    }

    // Récupérer l'utilisateur existant
    User user = optionalUser.get();
    
    // Inverser l'état de l'utilisateur (bloquer ou débloquer)
    user.setEnabled(!user.isEnabled());

    // Sauvegarder les modifications
    userRepository.save(user);

    String action = user.isEnabled() ? "débloqué" : "bloqué";
    return ResponseEntity.ok("Utilisateur " + action + " avec succès !");
}

}
