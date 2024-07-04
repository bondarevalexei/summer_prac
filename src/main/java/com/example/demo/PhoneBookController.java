package com.example.demo;

import com.example.demo.model.PhoneBookEntry;
import com.example.demo.repository.PhoneBookEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PhoneBookController {

    @Autowired
    private PhoneBookEntryRepository repository;

    @GetMapping("/dbForm")
    public String showForm(Model model) {
        model.addAttribute("phoneBookEntry", new PhoneBookEntry());
        model.addAttribute("entries", repository.findAll());
        return "form";
    }

    @PostMapping("/add")
    public String addEntry(@ModelAttribute PhoneBookEntry phoneBookEntry, Model model) {
        repository.save(phoneBookEntry);
        model.addAttribute("entries", repository.findAll());
        return "formResult";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<PhoneBookEntry> entry = repository.findById(id);
        if (entry.isPresent()) {
            model.addAttribute("phoneBookEntry", entry.get());
            return "editForm";
        } else {
            model.addAttribute("entries", repository.findAll());
            return "formResult";
        }
    }

    @PostMapping("/update/{id}")
    public String updateEntry(@PathVariable("id") Long id, @ModelAttribute PhoneBookEntry phoneBookEntry, Model model) {
        phoneBookEntry.setId(Math.toIntExact(id));
        repository.save(phoneBookEntry);
        return "redirect:/formResult";
    }

    @GetMapping("/formResult")
    public String resultForm(Model model){
        model.addAttribute("entries", repository.findAll());
        return "formResult";
    }

    @GetMapping("/delete/{id}")
    public String deleteEntry(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:/formResult";
    }
}