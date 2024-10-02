package com.reservation.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    private final SecretKey secretKey; // Déclarez votre clé secrète ici

    public JwtUtils() {
        // Créez une clé secrète à partir d'une chaîne, assurez-vous qu'elle a une longueur suffisante
        this.secretKey = Keys.hmacShaKeyFor("G!x9&fT@2sW#qE4*eP9t#Zr!dU2kM^zV".getBytes()); 
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 jour d'expiration
                .signWith(secretKey) // Utilisez la clé secrète ici
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // Utilisez setSigningKey(SecretKey) ici
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return parseToken(token).getSubject(); // Utilisez la méthode parseToken pour obtenir les informations
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }
}
