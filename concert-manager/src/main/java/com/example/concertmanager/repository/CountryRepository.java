package com.example.concertmanager.repository;

import com.example.concertmanager.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
