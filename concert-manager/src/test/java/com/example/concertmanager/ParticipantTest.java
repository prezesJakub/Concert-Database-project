package com.example.concertmanager;

import com.example.concertmanager.dto.ParticipantRequestDto;
import com.example.concertmanager.entity.Country;
import com.example.concertmanager.entity.Participant;
import com.example.concertmanager.repository.*;
import com.example.concertmanager.service.ParticipantService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ParticipantTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    private String getUrl() {
        return "http://localhost:" + port + "/api/participants";
    }

    @Test
    public void testCreateParticipantWithNewCountry() {
        ParticipantRequestDto dto = new ParticipantRequestDto();
        dto.setFirstName("Anna");
        dto.setLastName("Nowak");
        dto.setEmail("anna@nowak.com");
        dto.setCountryName("Poland");

        ResponseEntity<Participant> response = restTemplate.postForEntity(getUrl(), dto, Participant.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Participant created = response.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getCountry().getName()).isEqualTo("Poland");
        assertThat(countryRepository.findAll()).hasSize(1);
    }

    @Test
    public void testCreateParticipantWithExistingCountry() {
        Country country = countryRepository.saveAndFlush(new Country("Poland"));

        ParticipantRequestDto dto = new ParticipantRequestDto();
        dto.setFirstName("Krzysztof");
        dto.setLastName("Zalewski");
        dto.setEmail("krzysiek@example.com");
        dto.setCountryName("Poland");

        Participant created = participantService.createParticipant(dto);
        assertThat(created).isNotNull();
        assertThat(created.getCountry().getId()).isEqualTo(country.getId());
        assertThat(countryRepository.findAll()).hasSize(1);
    }
}
