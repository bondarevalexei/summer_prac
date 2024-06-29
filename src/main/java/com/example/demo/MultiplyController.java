package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/multiply")
public class MultiplyController {

    @PostMapping
    public ResponseEntity<Map<String, Integer>> multiply(@RequestBody Map<String, Integer> request) {
        int number = request.get("number");
        int multiplier = request.get("multiplier");
        int result = number * multiplier;

        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }
}