package com.example.concertmanager.controller;

import com.example.concertmanager.entity.City;
import com.example.concertmanager.entity.Country;
import com.example.concertmanager.repository.CityRepository;
import com.example.concertmanager.repository.CountryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public CityController(CityRepository cityRepository, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<City> create(@RequestBody City city) {
        if(city.getCountry() == null || city.getCountry().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Country> country = countryRepository.findById(city.getCountry().getId());
        if(country.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        city.setCountry(country.get());
        return ResponseEntity.ok(cityRepository.save(city));
    }
}
