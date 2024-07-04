package com.example.demo.repository;

import com.example.demo.model.VerificationToken;
import com.example.demo.repository.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    boolean existsByToken(String token);
    Optional<VerificationToken> findByUser(User user);
}