package com.example.concertmanager.controller;

import com.example.concertmanager.entity.Venue;
import com.example.concertmanager.repository.VenueRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueRepository venueRepository;

    public VenueController(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
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
    public Venue createVenue(@RequestBody Venue venue) {
        return venueRepository.save(venue);
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
