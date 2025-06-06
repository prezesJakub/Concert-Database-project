package com.example.concertmanager.controller;

import com.example.concertmanager.entity.Event;
import com.example.concertmanager.entity.Participant;
import com.example.concertmanager.entity.Seat;
import com.example.concertmanager.entity.Ticket;
import com.example.concertmanager.repository.EventRepository;
import com.example.concertmanager.repository.ParticipantRepository;
import com.example.concertmanager.repository.SeatRepository;
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
    private final SeatRepository seatRepository;

    public TicketController(TicketRepository ticketRepository, EventRepository eventRepository,
                            ParticipantRepository participantRepository, SeatRepository seatRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
        this.seatRepository = seatRepository;
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
        if (ticket.getEvent() == null || ticket.getParticipant() == null || ticket.getSeat() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Event> event = eventRepository.findById(ticket.getEvent().getId());
        Optional<Participant> participant = participantRepository.findById(ticket.getParticipant().getId());
        Optional<Seat> seat = seatRepository.findById(ticket.getSeat().getId());

        if(event.isEmpty() || participant.isEmpty() || seat.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        boolean seatAlreadyTaken = ticketRepository.existsByEventIdAndSeatId(
                ticket.getEvent().getId(),
                ticket.getSeat().getId()
        );

        if(seatAlreadyTaken) {
            return ResponseEntity.status(409).body(null);
        }

        ticket.setEvent(event.get());
        ticket.setParticipant(participant.get());
        ticket.setSeat(seat.get());

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
