package com.reservation.service;

import com.reservation.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retournez les rôles de l'utilisateur ici
        return null; // Remplacez par l'implémentation appropriée
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Remplacez par le bon champ
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Remplacez par le bon champ
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implémentez la logique appropriée
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implémentez la logique appropriée
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implémentez la logique appropriée
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled(); // Remplacez par le bon champ
    }
}
