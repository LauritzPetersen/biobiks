package com.lauritz.biobiks.Service;

import com.lauritz.biobiks.Model.User;
import com.lauritz.biobiks.Model.intface.UserRepository;
import com.lauritz.biobiks.Service.validation.UserValidation;
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


    public List<String> registerUser(User user) {
        List<String> errors = new ArrayList<>();

        validation.validateNewUser(user, errors);

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            errors.add("E-mailen '" + user.getEmail() + "' er allerede i brug");
        }

        if(errors.isEmpty()){
            userRepository.save(user);
        }

        return errors;
    }
}