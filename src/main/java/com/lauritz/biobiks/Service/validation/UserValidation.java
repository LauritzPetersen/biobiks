package com.lauritz.biobiks.Service.validation;

import com.lauritz.biobiks.Model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserValidation {

   public ValidationResult validateNewUser(User user){

       ValidationResult result = new ValidationResult();

       if(user.getName() == null || user.getName().trim().isEmpty()){
           result.addError("Du skal indtaste et navn");
       }

       if(user.getEmail() == null || !user.getEmail().contains("@")){
           result.addError("Du skal indtaste en  gyldig e-mailadresse (skal indholde @).");
       }

       if(user.getAge() < 13){
           result.addError("Du skal være mindst 13 år for at oprette en bruger i BioBiks");
       }

       return result;
   }
}
