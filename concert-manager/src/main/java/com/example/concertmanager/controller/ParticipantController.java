package com.example.concertmanager.controller;

import com.example.concertmanager.entity.Participant;
import com.example.concertmanager.repository.CountryRepository;
import com.example.concertmanager.repository.ParticipantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    private final ParticipantRepository participantRepository;
    private final CountryRepository countryRepository;

    public ParticipantController(ParticipantRepository participantRepository,
                                 CountryRepository countryRepository) {
        this.participantRepository = participantRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable Long id) {
        Optional<Participant> participant = participantRepository.findById(id);
        return participant.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant) {
        if(participant.getCountry() != null && participant.getCountry().getId() != null) {
            return countryRepository.findById(participant.getCountry().getId())
                    .map(country -> {
                        participant.setCountry(country);
                        return ResponseEntity.ok(participantRepository.save(participant));
                    })
                    .orElse(ResponseEntity.badRequest().build());
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participant> updateParticipant(@PathVariable Long id, @RequestBody Participant updated) {
        return participantRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(updated.getFirstName());
                    existing.setLastName(updated.getLastName());
                    existing.setEmail(updated.getEmail());
                    participantRepository.save(existing);
                    return ResponseEntity.ok(existing);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        if(participantRepository.existsById(id)) {
            participantRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
