package com.lauritz.biobiks.Controller;

import com.lauritz.biobiks.Model.User;
import com.lauritz.biobiks.Service.UserService;
import com.lauritz.biobiks.Service.validation.ValidationResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", loggedInUser);
        return "profile";
    }

    @PostMapping("/profile/add-funds")
    public String addFunds(@RequestParam double amount, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            ValidationResult result = userService.addFunds(loggedInUser, amount);

            if (result.hasErrors()) {
                model.addAttribute("user", loggedInUser); // Bordet skal bruge brugeren for at tegne siden
                model.addAttribute("errors", result.getErrors()); // Send de røde fejl med
                return "profile"; // Bliv på siden
            }
            session.setAttribute("loggedInUser", loggedInUser);
        }

        return "redirect:/profile";
    }
}
