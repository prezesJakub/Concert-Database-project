package com.example.concertmanager.controller;

import com.example.concertmanager.entity.Venue;
import com.example.concertmanager.repository.CityRepository;
import com.example.concertmanager.repository.VenueRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueRepository venueRepository;
    private final CityRepository cityRepository;

    public VenueController(VenueRepository venueRepository, CityRepository cityRepository) {
        this.venueRepository = venueRepository;
        this.cityRepository = cityRepository;
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
    public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {
        if(venue.getCity() != null && venue.getCity().getId() != null) {
            return cityRepository.findById(venue.getCity().getId())
                    .map(city -> {
                        venue.setCity(city);
                        return ResponseEntity.ok(venueRepository.save(venue));
                    })
                    .orElse(ResponseEntity.badRequest().build());
        }
        return ResponseEntity.badRequest().build();
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
