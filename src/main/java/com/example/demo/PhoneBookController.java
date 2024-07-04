package com.example.demo;

import com.example.demo.model.PhoneBookEntry;
import com.example.demo.repository.PhoneBookEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PhoneBookController {

    @Autowired
    private PhoneBookEntryRepository repository;

    private static final int PAGE_SIZE = 5;

    @GetMapping("/dbForm")
    public String showForm(Model model) {
        model.addAttribute("phoneBookEntry", new PhoneBookEntry());
        return "form";
    }

    @PostMapping("/add")
    public String addEntry(@ModelAttribute PhoneBookEntry phoneBookEntry) {
        repository.save(phoneBookEntry);
        return "redirect:/formResult";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<PhoneBookEntry> entry = repository.findById(id);
        if (entry.isPresent()) {
            model.addAttribute("phoneBookEntry", entry.get());
            return "editForm";
        } else {
            return "redirect:/formResult";
        }
    }

    @PostMapping("/update/{id}")
    public String updateEntry(@PathVariable("id") Long id, @ModelAttribute PhoneBookEntry phoneBookEntry) {
        phoneBookEntry.setId(Math.toIntExact(id));
        repository.save(phoneBookEntry);
        return "redirect:/formResult";
    }

    @GetMapping("/formResult")
    public String resultForm(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<PhoneBookEntry> entryPage = repository.findAll(PageRequest.of(page, PAGE_SIZE));
        model.addAttribute("entryPage", entryPage);
        return "formResult";
    }

    @GetMapping("/delete/{id}")
    public String deleteEntry(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:/formResult";
    }
}