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

       if(user.getPassword() == null || user.getPassword().trim().isEmpty()){
           result.addError("Du skal indtaste et password");
       }
       else if(user.getPassword().length() < 6){
           result.addError("Dit password skal være mindst 6 tegn langt");
       }

       if(user.getAge() < 13){
           result.addError("Du skal være mindst 13 år for at oprette en bruger i BioBiks");
       }
       return result;
   }

    public ValidationResult validateFundAddition(double amount){
        ValidationResult result = new ValidationResult();

        if(amount <= 0){
            result.addError("Du kan kun indsætte et positivt beløb på din konto.");
        }

        if(amount > 1000){
            result.addError("Du kan ikke indsætte mere end 1000 kr. ad gangen.");
        }
        return result;
    }
}
