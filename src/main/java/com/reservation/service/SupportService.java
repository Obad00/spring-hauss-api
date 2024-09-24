package com.reservation.service;

import com.reservation.entity.Support;
import com.reservation.repository.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupportService {

    @Autowired
    private SupportRepository supportRepository;

    public List<Support> getAllSupports() {
        return supportRepository.findAll();
    }

    public Optional<Support> getSupportById(Long id) {
        return supportRepository.findById(id);
    }

    public Support createSupport(Support support) {
        return supportRepository.save(support);
    }

    public Support updateSupport(Long id, Support updatedSupport) {
        return supportRepository.findById(id).map(support -> {
            support.setType_demande(updatedSupport.getType_demande());
            support.setUser(updatedSupport.getUser());
            return supportRepository.save(support);
        }).orElseThrow(() -> new RuntimeException("Support not found with id " + id));
    }

    public void deleteSupport(Long id) {
        supportRepository.deleteById(id);
    }
}
