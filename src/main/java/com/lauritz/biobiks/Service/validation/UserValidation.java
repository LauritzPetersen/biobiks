package com.lauritz.biobiks.Service.validation;

import com.lauritz.biobiks.Model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserValidation {

    public void validateNewUser(User user, List<String> errors) {
        if(user.getName() == null || user.getName().trim().isEmpty()){
            errors.add("Indtast et navn");
        }

        if(user.getEmail() == null || !user.getEmail().contains("@")) {
            errors.add("Du skal indtaste en gyldig e-mailadresse (skal indeholde @)");
        }

        if(user.getAge() < 13) {
            errors.add("Du skal være 13 for at oprette en konto");
        }
    }
}
