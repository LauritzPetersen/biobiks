package com.lauritz.biobiks.Service.validation;

import com.lauritz.biobiks.Model.Movie;
import com.lauritz.biobiks.Model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationValidation {

    public void validateNewReservation(User user, List<Movie> selectedMovies, List<String> errors){

        if(selectedMovies == null || selectedMovies.isEmpty()) {
            errors.add("Ordre skal indeholde minimum en biografbillet");
        }
        else{
            for(Movie movie : selectedMovies){
                if(user.getAge() < movie.getAgeLimit()){
                    errors.add("Du er for ung til at se '" + movie.getTitle() + "'. Minimumsalderen er " + movie.getAgeLimit() + " år");
                }
            }
        }

    }
}
