package com.lauritz.biobiks.Service;

import com.lauritz.biobiks.Model.User;
import com.lauritz.biobiks.Model.intface.UserRepository;
import com.lauritz.biobiks.Service.validation.UserValidation;
import com.lauritz.biobiks.Service.validation.ValidationResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // Husk denne!
public class UserService {

    private final UserRepository userRepository;
    private final UserValidation validation;

    public UserService(UserRepository userRepository, UserValidation validation) {
        this.userRepository = userRepository;
        this.validation = validation;
    }


    public Optional<User> loginUser(String email) {
        return userRepository.findByEmail(email);
    }


    public ValidationResult registerUser(User user) {

       ValidationResult result = validation.validateNewUser(user);

       if(userRepository.findByEmail(user.getEmail()).isPresent()){
           result.addError("E-mailen '" + user.getEmail() + "' er allerede i brug!");
       }

       if(!result.hasErrors()){
           userRepository.save(user);
       }

       return result;
    }
}