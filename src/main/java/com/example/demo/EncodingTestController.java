package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EncodingTestController {

    @GetMapping("/encoding-test")
    public ResponseEntity<String> testEncoding() {
        return ResponseEntity.ok("Encoding test successful!");
    }

    @GetMapping("/secure/test")
    public ResponseEntity<String> secureEndpoint() {
        return ResponseEntity.ok("Secure test successful!");
    }
}