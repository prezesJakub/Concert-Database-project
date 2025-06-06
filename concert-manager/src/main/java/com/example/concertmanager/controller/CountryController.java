package com.example.concertmanager.controller;

import com.example.concertmanager.entity.Country;
import com.example.concertmanager.repository.CountryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryRepository countryRepository;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Country> create(@RequestBody Country country) {
        return ResponseEntity.ok(countryRepository.save(country));
    }
}