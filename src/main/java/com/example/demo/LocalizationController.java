package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class LocalizationController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/welcome")
    public String welcome(@RequestParam(name = "lang", required = false) Locale locale) {
        return messageSource.getMessage("welcome.message", null, locale);
    }
}