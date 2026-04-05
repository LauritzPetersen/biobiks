package com.lauritz.biobiks.Controller;

import com.lauritz.biobiks.Service.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final MovieService movieService;

    public HomeController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String showFrontPage(Model model, HttpSession session) {

        model.addAttribute("movies", movieService.getAllMovies());

        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));

        return "index";
    }
}
