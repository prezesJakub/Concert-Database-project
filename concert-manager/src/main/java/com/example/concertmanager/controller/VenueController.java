package com.example.concertmanager.controller;

import com.example.concertmanager.dto.VenueRequestDto;
import com.example.concertmanager.entity.Venue;
import com.example.concertmanager.repository.CityRepository;
import com.example.concertmanager.repository.CountryRepository;
import com.example.concertmanager.repository.VenueRepository;
import com.example.concertmanager.service.VenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueRepository venueRepository;
    private final CityRepository cityRepository;
    private final VenueService venueService;

    public VenueController(VenueRepository venueRepository, CityRepository cityRepository,
                           VenueService venueService) {
        this.venueRepository = venueRepository;
        this.cityRepository = cityRepository;
        this.venueService = venueService;
    }

    @GetMapping
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        return venueRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Venue> createVenue(@RequestBody VenueRequestDto dto) {
        Venue venue = venueService.createVenue(dto);
        return ResponseEntity.ok(venue);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenueById(@PathVariable Long id) {
        if(venueRepository.existsById(id)) {
            venueRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
