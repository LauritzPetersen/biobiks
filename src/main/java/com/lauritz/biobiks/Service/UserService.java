package com.lauritz.biobiks.Service;

import com.lauritz.biobiks.Model.User;
import com.lauritz.biobiks.Model.intface.UserRepository;
import com.lauritz.biobiks.Service.validation.UserValidation;
import com.lauritz.biobiks.Service.validation.ValidationResult;
import org.mindrot.jbcrypt.BCrypt;
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


    public Optional<User> loginUser(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if(userOpt.isPresent()){
            User user = userOpt.get();

            try {
                if (BCrypt.checkpw(rawPassword, user.getPassword())) {
                    return Optional.of(user);
                }
            } catch (IllegalArgumentException e){
                return Optional.empty();
            }
        }
        return Optional.empty();

    }

    public ValidationResult registerUser(User user) {

       ValidationResult result = validation.validateNewUser(user);

       if(userRepository.findByEmail(user.getEmail()).isPresent()){
           result.addError("E-mailen '" + user.getEmail() + "' er allerede i brug!");
       }

       if(!result.hasErrors()){
           String hashedPw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
           user.setPassword(hashedPw);

           userRepository.save(user);
       }

       return result;
    }

    public ValidationResult addFunds(User user, double amount){

        ValidationResult result = validation.validateFundAddition(amount);

        if (!result.hasErrors()) {
            user.setBalance(user.getBalance() + amount);
            userRepository.updateBalance(user);
        }

        return result;
    }

}