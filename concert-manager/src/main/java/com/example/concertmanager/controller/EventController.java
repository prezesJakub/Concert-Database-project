package com.example.concertmanager.controller;

import com.example.concertmanager.entity.Category;
import com.example.concertmanager.entity.Event;
import com.example.concertmanager.entity.Organizer;
import com.example.concertmanager.entity.Venue;
import com.example.concertmanager.repository.CategoryRepository;
import com.example.concertmanager.repository.EventRepository;
import com.example.concertmanager.repository.OrganizerRepository;
import com.example.concertmanager.repository.VenueRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final VenueRepository venueRepository;
    private final CategoryRepository categoryRepository;

    public EventController(EventRepository eventRepository, OrganizerRepository organizerRepository,
                           VenueRepository venueRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.venueRepository = venueRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        if (event.getOrganizer() == null || event.getOrganizer().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Optional<Organizer> organizer = organizerRepository.findById(event.getOrganizer().getId());
        if (organizer.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        if (event.getVenue() == null || event.getVenue().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<Venue> venueOpt = venueRepository.findById(event.getVenue().getId());
        if (venueOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        if (event.getCategory() == null || event.getCategory().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<Category> categoryOpt = categoryRepository.findById(event.getCategory().getId());
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        event.setOrganizer(organizer.get());
        event.setVenue(venueOpt.get());
        event.setCategory(categoryOpt.get());

        Event saved = eventRepository.save(event);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updated) {
        return eventRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updated.getTitle());
                    existing.setDescription(updated.getDescription());
                    existing.setStartDate(updated.getStartDate());
                    existing.setEndDate(updated.getEndDate());

                    if(updated.getOrganizer() != null && updated.getOrganizer().getId() != null) {
                        organizerRepository.findById(updated.getOrganizer().getId())
                                .ifPresent(existing::setOrganizer);
                    }

                    if (updated.getVenue() != null && updated.getVenue().getId() != null) {
                        venueRepository.findById(updated.getVenue().getId())
                                .ifPresent(existing::setVenue);
                    }

                    if (updated.getCategory() != null && updated.getCategory().getId() != null) {
                        categoryRepository.findById(updated.getCategory().getId())
                                .ifPresent(existing::setCategory);
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
