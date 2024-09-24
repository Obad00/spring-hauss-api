package com.reservation.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotBlank(message = "Le nom est requis.")
    private String nom;

    @NotBlank(message = "Le prénom est requis.")
    private String prenom;

    @NotBlank(message = "L'adresse est requise.")
    private String adresse;

    @NotBlank(message = "L'email est requis.")
    @Email(message = "L'email doit être valide.")
    private String email;

    @NotBlank(message = "Le mot de passe est requis.")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères.")
    private String password;

    @NotBlank(message = "Le numéro de téléphone est requis.")
    private String telephone;

    @NotBlank(message = "Le rôle est requis.")
    private String role; // Champ pour le rôle

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
