package com.example.concertmanager.service;

import com.example.concertmanager.entity.*;
import com.example.concertmanager.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TicketServiceTest {

    @Autowired private TicketRepository ticketRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private ParticipantRepository participantRepository;
    @Autowired private SeatRepository seatRepository;
    @Autowired private VenueRepository venueRepository;
    @Autowired private OrganizerRepository organizerRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private CountryRepository countryRepository;
    @Autowired private CityRepository cityRepository;

    @Autowired private TicketService ticketService;

    private Event event;
    private Seat seat;
    private Participant participant;

    @BeforeEach
    @Transactional
    public void setUp() {
        Country country = countryRepository.save(new Country("Poland"));
        City city = cityRepository.save(new City("Warsaw", country));
        Venue venue = venueRepository.save(new Venue("Stadion Narodowy", "Duży stadion", 58000, city));
        Category category = categoryRepository.save(new Category("Koncert"));
        Organizer organizer = organizerRepository.save(new Organizer("Live Nation", "info@livenation.pl", "123456789"));

        event = new Event(
                "Metallica Live",
                "Koncert zespołu Metallica",
                venue,
                category,
                LocalDateTime.now().plusDays(30),
                LocalDateTime.now().plusDays(30).plusHours(2),
                organizer,
                199.99,
                149.99,
                299.99
        );

        event = eventRepository.save(event);

        seat = seatRepository.save(new Seat("12", "A", "VIP", venue));
        participant = participantRepository.save(new Participant("Jan", "Kowalski", "jan.kowalski@example.com", country));
    }

    @Test
    @Transactional
    public void testSuccessfulTicketReservation() {
        Ticket ticket = ticketService.reserveSeat(
                participant.getId(),
                event.getId(),
                seat.getId(),
                TicketType.REGULAR,
                199.99
        );

        assertNotNull(ticket.getId());
        assertEquals(event.getId(), ticket.getEvent().getId());
        assertEquals(participant.getId(), ticket.getParticipant().getId());
        assertEquals(seat.getId(), ticket.getSeat().getId());
        assertEquals(TicketType.REGULAR, ticket.getType());
    }

    @Test
    @Transactional
    public void testDuplicateSeatReservationFails() {
        ticketService.reserveSeat(
                participant.getId(),
                event.getId(),
                seat.getId(),
                TicketType.REGULAR,
                199.99
        );

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            ticketService.reserveSeat(
                    participant.getId(),
                    event.getId(),
                    seat.getId(),
                    TicketType.REGULAR,
                    199.99
            );
        });

        assertEquals("Seat is already reserved for this event", exception.getMessage());
    }
}
