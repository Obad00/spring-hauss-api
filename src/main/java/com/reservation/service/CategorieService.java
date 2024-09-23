package com.reservation.service;

import com.reservation.entity.Categorie;
import com.reservation.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Categorie getCategorieById(Long id) {
        return categorieRepository.findById(id).orElse(null);
    }

    public Categorie createCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Categorie updateCategorie(Long id, Categorie categorieDetails) {
        Categorie categorie = categorieRepository.findById(id).orElse(null);
        if (categorie != null) {
            categorie.setNom(categorieDetails.getNom());
            return categorieRepository.save(categorie);
        }
        return null;
    }

    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }
}
