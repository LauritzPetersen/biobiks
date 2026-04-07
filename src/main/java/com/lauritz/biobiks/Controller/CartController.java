package com.lauritz.biobiks.Controller;

import com.lauritz.biobiks.Model.Reservation;
import com.lauritz.biobiks.Model.Ticket;
import com.lauritz.biobiks.Model.User;
import com.lauritz.biobiks.Service.MovieService;
import com.lauritz.biobiks.Service.ReservationService;
import com.lauritz.biobiks.Service.SnackService;
import com.lauritz.biobiks.Service.validation.TicketValidation;
import com.lauritz.biobiks.Service.validation.ValidationResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
public class CartController {

    private final MovieService movieService;
    private final ReservationService reservationService;
    private final SnackService snackService;
    private final TicketValidation ticketValidation;

    public CartController(MovieService movieService,
                          ReservationService reservationService,
                          SnackService snackService,
                          TicketValidation ticketValidation){
        this.movieService = movieService;
        this.reservationService = reservationService;
        this.snackService = snackService;
        this.ticketValidation = ticketValidation;
    }

    private Reservation getCart(HttpSession session){
        Reservation cart = (Reservation) session.getAttribute("cart");
        if(cart == null){
            cart = new Reservation();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @PostMapping("/cart/add-ticket")
    public String addTicketToCart(@RequestParam int movieId,
                                  @RequestParam LocalDate showDate,
                                  @RequestParam LocalTime showTime,
                                  @RequestParam int quantity,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        ValidationResult result = ticketValidation.validateTicketQuantity(quantity);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", result.getErrors());
            return "redirect:/movie/" + movieId;
        }

        Reservation cart = getCart(session);

        movieService.getMovieById(movieId).ifPresent(movie -> {
            for (int i = 0; i < quantity; i++) {
                Ticket nyBillet = new Ticket(movie, showDate, showTime);
                cart.addTicket(nyBillet);
            }
        });

        return "redirect:/movies";
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

    @PostMapping("/cart/remove-ticket")
    public String removeTicketFromCart(@RequestParam int ticketIndex, HttpSession session) {
        Reservation cart = getCart(session);

        // Vi fjerner billetten baseret på dens plads i listen (index)
        if (ticketIndex >= 0 && ticketIndex < cart.getTickets().size()) {
            Ticket ticketToRemove = cart.getTickets().get(ticketIndex);
            cart.setTotalPrice(cart.getTotalPrice() - ticketToRemove.getMovie().getPrice());
            cart.getTickets().remove(ticketIndex);
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
        // Opdaterer den loggede bruger i sessionen, fordi deres balance måske er ændret
        session.setAttribute("loggedInUser", loggedInUser);

        return "redirect:/cart/confirmation";
    }

    @GetMapping("/cart/confirmation")
    public String showConfirmation(Model model, HttpSession session) {
        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));
        return "confirmation";
    }

}
