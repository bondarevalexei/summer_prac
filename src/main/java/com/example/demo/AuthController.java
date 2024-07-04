package com.example.demo;

import com.example.demo.model.VerificationToken;
import com.example.demo.repository.User;
import com.example.demo.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.LocalDateTime;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("usernameError", "Username already exists");
            return "register";
        }

        userService.save(user);
        return "redirect:/login?message=Please check your email for a confirmation link.";
    }

    @GetMapping("/login")
    public String showLoginForm(HttpServletRequest request, Model model) {
        String error = request.getParameter("error");
        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password.");
        }
        String message = request.getParameter("message");
        if (message != null) {
            model.addAttribute("loginMessage", message);
        }
        return "login";
    }

    @GetMapping("/confirm")
    public String confirmRegistration(@RequestParam("token") String token, Model model) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            model.addAttribute("message", "Invalid token.");
            return "error";
        }

        User user = verificationToken.getUser();
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("message", "Token has expired.");
            return "error";
        }

        user.setEnabled(true);
        userService.update(user);  // Используем метод update вместо save

        model.addAttribute("message", "Your account has been confirmed. You can now log in.");
        return "login";
    }
}