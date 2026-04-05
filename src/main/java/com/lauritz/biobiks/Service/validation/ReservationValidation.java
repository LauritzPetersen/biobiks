package com.lauritz.biobiks.Service.validation;

import com.lauritz.biobiks.Model.Movie;
import com.lauritz.biobiks.Model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationValidation {

    public ValidationResult validateNewReservation(User user, List<Movie> selectedMovies){

        ValidationResult result = new ValidationResult();

        if(selectedMovies == null || selectedMovies.isEmpty()) {
            result.addError("Ordre skal indeholde minimum en biografbillet");
        }
        else{
            for(Movie movie : selectedMovies){
                if(user.getAge() < movie.getAgeLimit()){
                    result.addError("Du er for ung til at se '" + movie.getTitle() + "'. Minimumsalderen er " + movie.getAgeLimit() + " år");
                }
            }
        }

        return result;
    }

}
