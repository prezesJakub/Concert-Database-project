package com.example.concertmanager.repository;

import com.example.concertmanager.entity.City;
import com.example.concertmanager.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByNameIgnoreCaseAndCountry(String name, Country country);
}