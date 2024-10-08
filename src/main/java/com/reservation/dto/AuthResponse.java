package com.reservation.dto;

import com.reservation.entity.User;

import com.reservation.enums.RoleUtilisateur; // Importez l'enum

public class AuthResponse {
    private String token;
    private RoleUtilisateur role; // Utilisez RoleUtilisateur comme type
    private User user;

    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = user;
        this.role = user.getRole(); // Assurez-vous que cette méthode existe
    }
    // Getters et setters


    public RoleUtilisateur getRole() {
        return role; // Retourne le rôle sous forme d'enum
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
