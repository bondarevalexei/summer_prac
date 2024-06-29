package com.example.demo;

import com.example.demo.repository.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

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
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("username") String username,
                            @ModelAttribute("password") String password,
                            Model model) {
        System.out.println(username);
        System.out.println(password);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            // Если аутентификация успешна, перенаправляем на домашнюю страницу
            return "redirect:/";
        } catch (AuthenticationException e) {
            // Если аутентификация неудачна, возвращаемся на страницу входа с сообщением об ошибке
            model.addAttribute("loginError", "Invalid username or password.");
            return "login";
        }
    }
}