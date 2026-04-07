package com.lauritz.biobiks.Controller;

import com.lauritz.biobiks.Model.Movie;
import com.lauritz.biobiks.Service.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public String showCatalog(Model model, HttpSession session){
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));
        return "catalog";
    }

    @GetMapping("/movie/{id}")
    public String showMovieDetails(@PathVariable("id") int id, Model model, HttpSession session){
        Optional<Movie> movieOpt = movieService.getMovieById(id);

        if(movieOpt.isPresent()){
            model.addAttribute("movie", movieOpt.get());
            model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));
            return "movie-details";
        }
        else{
            return "redirect:/movies";
        }
    }
}
