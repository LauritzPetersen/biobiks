package com.lauritz.biobiks.Controller;

import com.lauritz.biobiks.Service.SnackService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SnackController {

    private final SnackService snackService;

    public SnackController(SnackService snackService){
        this.snackService = snackService;
    }

    @GetMapping("/kiosk")
    public String showKiosk(Model model, HttpSession session){

        model.addAttribute("snacks", snackService.getAllSnacks());

        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));

        return "kiosk";

    }
}
