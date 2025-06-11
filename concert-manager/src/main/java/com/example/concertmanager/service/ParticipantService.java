package com.example.concertmanager.service;

import com.example.concertmanager.dto.ParticipantRequestDto;
import com.example.concertmanager.entity.Country;
import com.example.concertmanager.entity.Participant;
import com.example.concertmanager.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final LocationService locationService;

    public ParticipantService(ParticipantRepository participantRepository, LocationService locationService) {
        this.participantRepository = participantRepository;
        this.locationService = locationService;
    }

    public Participant createParticipant(ParticipantRequestDto dto) {
        Country country = locationService.findOrCreateCountry(dto.getCountryName());

        Participant participant = new Participant(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                country
        );

        return participantRepository.save(participant);
    }
}
