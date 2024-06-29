package com.example.demo;

import com.example.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @GetMapping("/userData")
    public String index(Model model) {
        model.addAttribute("user", new User());
        return "userData";
    }

    @PostMapping("/resultUser")
    public String result(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "resultUser";
    }

    @GetMapping("/json")
    @ResponseBody
    public User json(@ModelAttribute User user) {
        return user;
    }


}