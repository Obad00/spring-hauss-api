package com.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.reservation.repository.UserRepository;
import com.reservation.entity.User;
import com.reservation.exception.UserNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public List<User> findByNomOrPrenom(String query) {
        List<User> usersByNom = userRepository.findByNomContaining(query);
        List<User> usersByPrenom = userRepository.findByPrenomContaining(query);
        
        // Combine the results without duplicates
        usersByNom.addAll(usersByPrenom);
        
        if (usersByNom.isEmpty()) {
            throw new UserNotFoundException("Aucun utilisateur trouvé avec le nom ou le prénom : " + query);
        }
        return usersByNom;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }
    
    
    
    
}
