package com.reservation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.reservation.entity.Logement;

public class LogementValidator implements ConstraintValidator<ValidLogement, Logement> {

    @Override
    public boolean isValid(Logement logement, ConstraintValidatorContext context) {
        if (logement == null) {
            return true; // null is valid
        }

        boolean valid = true;

        if (logement.getTitre() == null || logement.getTitre().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Le titre ne peut pas être vide")
                   .addPropertyNode("titre")
                   .addConstraintViolation();
            valid = false;
        }

    

        // Ajoutez d'autres validations ici...

        if (logement.getPrix() == null || logement.getPrix() <= 0) {
            context.buildConstraintViolationWithTemplate("Le prix doit être supérieur à zéro")
                   .addPropertyNode("prix")
                   .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
