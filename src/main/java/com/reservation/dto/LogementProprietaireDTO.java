package com.reservation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogementProprietaireDTO  {
    private Long id;
    private String titre;
    private Double prix;
    private String description;
    private Integer nombre_chambre;
    private Integer nombre_toilette;
    private String adresseRegion;
    private String adresseLocalite;
    private String categorie;
    private int nombreUtilisateursFavoris;
}
