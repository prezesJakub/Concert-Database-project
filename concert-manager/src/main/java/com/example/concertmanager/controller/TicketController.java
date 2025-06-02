package com.example.concertmanager.controller;

import com.example.concertmanager.entity.Event;
import com.example.concertmanager.entity.Participant;
import com.example.concertmanager.entity.Ticket;
import com.example.concertmanager.repository.EventRepository;
import com.example.concertmanager.repository.ParticipantRepository;
import com.example.concertmanager.repository.TicketRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    public TicketController(TicketRepository ticketRepository, EventRepository eventRepository, ParticipantRepository participantRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ticketRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Optional<Event> event = eventRepository.findById(ticket.getEvent().getId());
        Optional<Participant> participant = participantRepository.findById(ticket.getParticipant().getId());

        if(event.isEmpty() || participant.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        ticket.setEvent(event.get());
        ticket.setParticipant(participant.get());

        Ticket saved = ticketRepository.save(ticket);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        if(ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
