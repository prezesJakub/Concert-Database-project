package com.example.concertmanager.controller;

import com.example.concertmanager.entity.Seat;
import com.example.concertmanager.entity.Venue;
import com.example.concertmanager.repository.SeatRepository;
import com.example.concertmanager.repository.VenueRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatRepository seatRepository;
    private final VenueRepository venueRepository;

    public SeatController(SeatRepository seatRepository, VenueRepository venueRepository) {
        this.seatRepository = seatRepository;
        this.venueRepository = venueRepository;
    }

    @GetMapping
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    @GetMapping("/venue/{venueId}")
    public List<Seat> getSeatsByVenue(@PathVariable Long venueId) {
        return seatRepository.findByVenueId(venueId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long id) {
        return seatRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Seat> createSeat(@RequestBody Seat seat) {
        if(seat.getVenue() == null || seat.getVenue().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Venue> venue = venueRepository.findById(seat.getVenue().getId());
        if(venue.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        seat.setVenue(venue.get());
        Seat saved = seatRepository.save(seat);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seat> updateSeat(@PathVariable Long id, @RequestBody Seat updated) {
        return seatRepository.findById(id)
                .map(seat -> {
                    seat.setSeatNumber(updated.getSeatNumber());
                    seat.setRow(updated.getRow());
                    seat.setSection(updated.getSection());

                    if (updated.getVenue() != null && updated.getVenue().getId() != null) {
                        venueRepository.findById(updated.getVenue().getId())
                                .ifPresent(seat::setVenue);
                    }

                    seatRepository.save(seat);
                    return ResponseEntity.ok(seat);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        if(seatRepository.existsById(id)) {
            seatRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
