package com.reservation.controller;

import com.reservation.entity.User;
import com.reservation.enums.RoleUtilisateur;
import com.reservation.repository.UserRepository;
import com.reservation.service.CustomUserDetailsService;
import com.reservation.service.EmailService;

import org.springframework.security.core.userdetails.UserDetails;
import com.reservation.utils.JwtUtils;


import com.reservation.dto.AuthRequest;
import com.reservation.dto.AuthResponse;
import com.reservation.dto.UserRegistrationDTO;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import com.reservation.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService emailService;

   
    @PostMapping("/register")
    public String register(@Valid @RequestBody UserRegistrationDTO userDTO, BindingResult bindingResult) {
        // Vérifiez si des erreurs de validation se sont produites
        if (bindingResult.hasErrors()) {
            return "Erreur de validation : " + bindingResult.getAllErrors().toString();
        }
    
        // Vérifiez l'unicité de l'email
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            return "Cet email est déjà utilisé!";
        }
    
        // Créez un nouvel utilisateur à partir du DTO
        User user = new User();
        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setAdresse(userDTO.getAdresse());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encoder le mot de passe
        user.setTelephone(userDTO.getTelephone());
        user.setRole(RoleUtilisateur.valueOf(userDTO.getRole())); // Assurez-vous que le rôle est valide
        user.setEnabled(true); // Utilisateur activé par défaut
    
        // Enregistrez l'utilisateur
        userRepository.save(user);
    
        // Envoyer un email de confirmation
        String emailSubject = "Bienvenue sur notre plateforme!";
        String emailText = "<html>" +
                "<head>" +
                "<style>" +
                "    body { font-family: Arial, sans-serif; background-color: #f7f7f7; padding: 20px; }" +
                "    .container { max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }" +
                "    h2 { color: #356F37; }" +
                "    p { font-size: 16px; line-height: 1.5; }" +
                "    .footer { margin-top: 20px; font-size: 12px; color: #777; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<h2>Bienvenue " + user.getPrenom() + "!</h2>" +
                "<p>Merci pour votre inscription sur notre plateforme.</p>" +
                "<p>Nous sommes ravis de vous avoir avec nous!</p>" +
                "<p>Cordialement,<br>L'équipe.</p>" +
                "</div>" +
                "<div class='footer'>Cet email a été généré automatiquement. Veuillez ne pas répondre.</div>" +
                "</body>" +
                "</html>";
    
        emailService.sendReservationEmail(user.getEmail(), emailSubject, emailText);
    
        return "Utilisateur enregistré avec succès!";
    }
    
    
        


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // Authentifier l'utilisateur
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Invalid credentials", e);
        }

        // Récupérer les détails de l'utilisateur (UserDetails)
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

        // Générer le token JWT
        final String jwt = jwtUtils.generateToken(userDetails.getUsername());

        // Récupérer l'utilisateur complet via ton service utilisateur (pour obtenir plus d'infos)
        User user = userService.findByEmail(authRequest.getEmail());

        // Créer une réponse avec le token et les informations utilisateur
        AuthResponse authResponse = new AuthResponse(jwt, user);

        // Retourner la réponse avec le token et l'utilisateur
        return ResponseEntity.ok(authResponse);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String token) {
        try {
            // Ajoutez ici la logique pour ajouter le token à une liste noire, si nécessaire.
            // Par exemple, vous pourriez le stocker dans une base de données ou un cache.
    
            return ResponseEntity.ok("Déconnexion réussie");
        } catch (Exception e) {
            // Log l'erreur
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Erreur lors de la déconnexion");
        }
    }
    



}
