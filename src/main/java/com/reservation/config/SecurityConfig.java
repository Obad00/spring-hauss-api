package com.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.reservation.filter.JwtFilter;

import java.util.List; // Ajoutez cette ligne


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public JwtFilter jwtFilter() throws Exception {
        return new JwtFilter(); // Crée une instance de votre filtre JWT
    }
    @Bean
    public SecurityFilterChain appEndpoints(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/api/reservations").authenticated() // Exige l'authentification pour ce point de terminaison
            .requestMatchers("/api/logements").authenticated() // Exige l'authentification pour ce point de terminaison

                .anyRequest().permitAll())
                .cors(customizer -> customizer.configurationSource(corsConfigurationSource()))// Modifiez cette ligne
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class); // Ajoutez le filtre ici

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Crée un bean PasswordEncoder
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        // Configurer les détails de l'authentification ici
        return authenticationManagerBuilder.build();
    }

    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080")); 
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Méthodes autorisées
        configuration.setAllowedHeaders(List.of("*")); // Autorise tous les headers
        configuration.setAllowCredentials(true); // Autorise les informations d'identification
        configuration.setMaxAge(3600L); // Durée de validité des CORS en secondes

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = 
            new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Applique la configuration CORS à toutes les routes

        return source;
    }
}
