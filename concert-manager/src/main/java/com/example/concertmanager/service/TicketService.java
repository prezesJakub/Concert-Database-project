package com.example.concertmanager.service;

import com.example.concertmanager.entity.*;
import com.example.concertmanager.repository.EventRepository;
import com.example.concertmanager.repository.ParticipantRepository;
import com.example.concertmanager.repository.SeatRepository;
import com.example.concertmanager.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;

    public TicketService(TicketRepository ticketRepository,
                         ParticipantRepository participantRepository,
                         EventRepository eventRepository,
                         SeatRepository seatRepository) {
        this.ticketRepository = ticketRepository;
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public Ticket reserveSeat(Long participantId, Long eventId, Long seatId, TicketType ticketType, Double price) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException("Participant not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found"));

        boolean isSeatTaken = ticketRepository.existsByEventIdAndSeatId(eventId, seatId);
        if (isSeatTaken) {
            throw new IllegalStateException("Seat is already reserved for this event");
        }

        Ticket ticket = new Ticket();
        ticket.setParticipant(participant);
        ticket.setEvent(event);
        ticket.setSeat(seat);
        ticket.setType(ticketType);
        ticket.setPrice(price);

        return ticketRepository.save(ticket);
    }
}
