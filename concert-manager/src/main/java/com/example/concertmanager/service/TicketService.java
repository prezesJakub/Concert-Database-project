package com.example.concertmanager.service;

import com.example.concertmanager.dto.TicketReservationRequestDto;
import com.example.concertmanager.entity.*;
import com.example.concertmanager.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;
    private final CountryRepository countryRepository;
    private final LocationService locationService;

    public TicketService(TicketRepository ticketRepository,
                         ParticipantRepository participantRepository,
                         EventRepository eventRepository,
                         SeatRepository seatRepository, CountryRepository countryRepository,
                         LocationService locationService) {
        this.ticketRepository = ticketRepository;
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
        this.countryRepository = countryRepository;
        this.locationService = locationService;
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

    public Ticket reserveSeatWithParticipantData(TicketReservationRequestDto dto) {
        Country country = locationService.findOrCreateCountry(dto.getCountryName());

        Participant participant = participantRepository
                .findByFirstNameAndLastNameAndEmailAndCountryName(
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getEmail(),
                        dto.getCountryName()
                )
                .orElseGet(() -> {
                    Participant newParticipant = new Participant(
                            dto.getFirstName(),
                            dto.getLastName(),
                            dto.getEmail(),
                            country
                    );
                    return participantRepository.save(newParticipant);
                });

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        Seat seat = seatRepository.findById(dto.getSeatId())
                .orElseThrow(() -> new EntityNotFoundException("Seat not found"));

        boolean seatTaken = ticketRepository.existsByEventIdAndSeatId(dto.getEventId(), dto.getSeatId());
        if (seatTaken) {
            throw new IllegalStateException("Seat is already reserved for this event");
        }

        Ticket ticket = new Ticket();
        ticket.setParticipant(participant);
        ticket.setEvent(event);
        ticket.setSeat(seat);
        ticket.setType(dto.getTicketType());
        ticket.setPrice(dto.getPrice());

        return ticketRepository.save(ticket);
    }
}
