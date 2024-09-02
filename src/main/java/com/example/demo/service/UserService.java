package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.VerificationToken;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    private RoleRepository roleRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);

        Role userRole = roleRepository.findByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

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