package com.example.concertmanager.controller;

import com.example.concertmanager.dto.TicketReservationRequestDto;
import com.example.concertmanager.entity.*;
import com.example.concertmanager.repository.EventRepository;
import com.example.concertmanager.repository.ParticipantRepository;
import com.example.concertmanager.repository.SeatRepository;
import com.example.concertmanager.repository.TicketRepository;
import com.example.concertmanager.service.TicketService;
import jakarta.persistence.EntityNotFoundException;
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
    private final TicketService ticketService;

    public TicketController(TicketRepository ticketRepository, EventRepository eventRepository,
                            ParticipantRepository participantRepository, SeatRepository seatRepository, TicketService ticketService) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
        this.seatRepository = seatRepository;
        this.ticketService = ticketService;
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

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveTicket(@RequestBody TicketReservationRequestDto dto) {
        try {
            Ticket ticket = ticketService.reserveSeatWithParticipantData(dto);
            return ResponseEntity.ok(ticket);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
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
