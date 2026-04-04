package com.lauritz.biobiks.Controller;

import com.lauritz.biobiks.Model.User;
import com.lauritz.biobiks.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("User", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute User user, Model model){

        List<String> errors = userService.registerUser(user);
        if(!errors.isEmpty()){
            model.addAttribute("errors", errors);
            return "register";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String processLogin(@RequestParam String email, HttpSession session, Model model) {
        Optional<User> userOpt = userService.loginUser(email);

        if (userOpt.isPresent()) {
            session.setAttribute("loggedInUser", userOpt.get());
            return "redirect:/";
        }
        else {
            model.addAttribute("error", "Hov! E-mailen findes ikke i vores system.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }



}
