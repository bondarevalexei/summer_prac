package com.example.demo;

import com.example.demo.model.VerificationToken;
import com.example.demo.repository.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationTokenService tokenService;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false); // Установим enabled в false до подтверждения email
        userRepository.save(user);

        // Создаем и отправляем токен
        VerificationToken token = tokenService.createOrUpdateToken(user);
        String confirmationUrl = "http://localhost:8080/confirm?token=" + token.getToken();
        emailService.sendMail(user.getEmail(), "Registration Confirmation", "Please confirm your registration by clicking the link: " + confirmationUrl);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public VerificationToken getVerificationToken(String token) {
        return tokenService.findByToken(token);
    }
}