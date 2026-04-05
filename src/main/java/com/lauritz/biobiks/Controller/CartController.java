package com.lauritz.biobiks.Controller;

import com.lauritz.biobiks.Model.Reservation;
import com.lauritz.biobiks.Model.User;
import com.lauritz.biobiks.Service.MovieService;
import com.lauritz.biobiks.Service.ReservationService;
import com.lauritz.biobiks.Service.SnackService;
import com.lauritz.biobiks.Service.validation.ValidationResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class CartController {

    private final MovieService movieService;
    private final ReservationService reservationService;
    private final SnackService snackService;

    public CartController(MovieService movieService, ReservationService reservationService, SnackService snackService){
        this.movieService = movieService;
        this.reservationService = reservationService;
        this.snackService = snackService;
    }

    private Reservation getCart(HttpSession session){
        Reservation cart = (Reservation) session.getAttribute("cart");
        if(cart == null){
            cart = new Reservation();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @PostMapping("/cart/add-movie")
    public String addMovieToCart(@RequestParam int movieId, HttpSession session){
        Reservation cart = getCart(session);
        movieService.getMovieById(movieId).ifPresent(cart::orderMovie);
        return "redirect:/";
    }

    @PostMapping("/cart/add-snack")
    public String addSnackToCart(@RequestParam int snackId, HttpSession session) {
        Reservation cart = getCart(session);

        // Find snack i databasen og tilføj den til kurven
        snackService.getSnackById(snackId).ifPresent(cart::orderSnack);

        return "redirect:/kiosk";
    }

    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        model.addAttribute("cart", getCart(session)); // Læg kurven på bordet
        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));
        return "cart";
    }

    @PostMapping("/cart/remove-movie")
    public String removeMovieFromCart(@RequestParam int movieId, HttpSession session) {
        Reservation cart = getCart(session);

        // Vi løber kurvens film igennem for at finde den, der skal slettes
        for (int i = 0; i < cart.getMovies().size(); i++) {
            if (cart.getMovies().get(i).getId() == movieId) {
                cart.setTotalPrice(cart.getTotalPrice() - cart.getMovies().get(i).getPrice());
                cart.getMovies().remove(i);
                break;
            }
        }
        return "redirect:/cart";
    }

    // POST: Fjerner en snack fra kurven
    @PostMapping("/cart/remove-snack")
    public String removeSnackFromCart(@RequestParam int snackId, HttpSession session) {
        Reservation cart = getCart(session);

        for (int i = 0; i < cart.getSnacks().size(); i++) {
            if (cart.getSnacks().get(i).getId() == snackId) {
                cart.setTotalPrice(cart.getTotalPrice() - cart.getSnacks().get(i).getPrice());
                cart.getSnacks().remove(i);
                break;
            }
        }
        return "redirect:/cart";
    }


    @PostMapping("/cart/checkout")
    public String checkout(HttpSession session, Model model){
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        Reservation cart = getCart(session);

        if(loggedInUser == null){
            return "redirect:/login";
        }

        cart.setUserId(loggedInUser.getId());
        cart.setReservationDate(LocalDateTime.now());

        ValidationResult result = reservationService.createReservation(loggedInUser, cart);
        if (result.hasErrors()) {
            model.addAttribute("cart", cart);
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("errors", result.getErrors());
            return "cart"; // Bliv på siden og vis den røde fejlboks
        }

        // SUCCES! Vi sletter kurven fra rygsækken, så den er tom til næste gang
        session.removeAttribute("cart");

        return "redirect:/cart/confirmation";
    }

    @GetMapping("/cart/confirmation")
    public String showConfirmation(Model model, HttpSession session) {
        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));
        return "confirmation";
    }

}
