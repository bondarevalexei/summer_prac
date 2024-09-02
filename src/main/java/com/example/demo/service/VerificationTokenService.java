package com.example.demo.service;

import com.example.demo.model.VerificationToken;
import com.example.demo.model.User;
import com.example.demo.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    public VerificationToken createOrUpdateToken(User user) {
        Optional<VerificationToken> optionalToken = tokenRepository.findByUser(user);

        VerificationToken token;
        if (optionalToken.isPresent()) {
            token = optionalToken.get();
            token.setToken(generateUniqueToken());
            token.setExpiryDate(calculateExpiryDate());
        } else {
            token = new VerificationToken();
            token.setToken(generateUniqueToken());
            token.setUser(user);
            token.setExpiryDate(calculateExpiryDate());
        }

        tokenRepository.save(token);
        return token;
    }

    private String generateUniqueToken() {
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (tokenRepository.existsByToken(token));
        return token;
    }

    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusHours(24); // Устанавливаем срок действия токена на 24 часа
    }

    public VerificationToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}