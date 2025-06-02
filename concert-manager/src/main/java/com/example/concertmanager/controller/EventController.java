package com.example.concertmanager.controller;

import com.example.concertmanager.entity.Event;
import com.example.concertmanager.entity.Organizer;
import com.example.concertmanager.repository.EventRepository;
import com.example.concertmanager.repository.OrganizerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;

    public EventController(EventRepository eventRepository, OrganizerRepository organizerRepository) {
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        if (event.getOrganizer() == null || event.getOrganizer().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Organizer> organizer = organizerRepository.findById(event.getOrganizer().getId());
        if (organizer.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        event.setOrganizer(organizer.get());
        Event saved = eventRepository.save(event);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updated) {
        return eventRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updated.getTitle());
                    existing.setDescription(updated.getDescription());
                    existing.setLocation(updated.getLocation());
                    existing.setStartDate(updated.getStartDate());
                    existing.setEndDate(updated.getEndDate());

                    if(updated.getOrganizer() != null && updated.getOrganizer().getId() != null) {
                        organizerRepository.findById(updated.getOrganizer().getId())
                                .ifPresent(existing::setOrganizer);
                    }

                    eventRepository.save(existing);
                    return ResponseEntity.ok(existing);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
