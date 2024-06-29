package com.example.demo;

import com.example.demo.model.PhoneBookEntry;
import com.example.demo.repository.PhoneBookEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PhoneBookController {

    @Autowired
    private PhoneBookEntryRepository repository;

    @GetMapping("/dbForm")
    public String showForm(Model model) {
        model.addAttribute("phoneBookEntry", new PhoneBookEntry());
        return "form";
    }

    @PostMapping("/add")
    public String addEntry(@ModelAttribute PhoneBookEntry phoneBookEntry, Model model) {
        repository.save(phoneBookEntry);
        model.addAttribute("entries", repository.findAll());
        return "formResult";
    }
}