package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/session/set")
    public String setSession(HttpSession session) {
        session.setAttribute("key", "value");
        return "Session attribute set";
    }

    @GetMapping("/session/get")
    public String getSession(HttpSession session) {
        return "Session attribute value: " + session.getAttribute("key");
    }
}