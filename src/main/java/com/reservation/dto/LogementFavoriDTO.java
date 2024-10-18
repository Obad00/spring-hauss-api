package com.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogementFavoriDTO {
    private Long id;
    private String titre;
    private Double prix;
    private String description;
    private Integer nombre_chambre;
    private Integer nombre_toilette;
    private String regions; // Nouveau champ pour les régions
    private String localite; // Nouveau champ pour la localité
    private String categorie; // Pour la catégorie
}
