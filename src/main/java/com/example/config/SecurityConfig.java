package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector).servletPath("/spring-mvc");
    }

    @Bean
    SecurityFilterChain appEndpoints(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Désactiver la protection CSRF pour faciliter les tests
            .authorizeHttpRequests(authorize -> authorize
            .anyRequest().permitAll() // Permettre l'accès à toutes les requêtes sans authentification
            );

        return http.build();
    }
}
