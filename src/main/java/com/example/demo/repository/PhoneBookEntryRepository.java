package com.example.demo.repository;

import com.example.demo.model.PhoneBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneBookEntryRepository extends JpaRepository<PhoneBookEntry, Long> {
}