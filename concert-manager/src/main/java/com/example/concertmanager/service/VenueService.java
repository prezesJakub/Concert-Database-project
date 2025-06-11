package com.example.concertmanager.service;

import com.example.concertmanager.dto.VenueRequestDto;
import com.example.concertmanager.entity.City;
import com.example.concertmanager.entity.Venue;
import com.example.concertmanager.repository.VenueRepository;
import org.springframework.stereotype.Service;

@Service
public class VenueService {

    private final VenueRepository venueRepository;
    private final LocationService locationService;

    public VenueService(VenueRepository venueRepository, LocationService locationService) {
        this.venueRepository = venueRepository;
        this.locationService = locationService;
    }

    public Venue createVenue(VenueRequestDto dto) {
        City city = locationService.findOrCreateCity(dto.getCityName(), dto.getCountryName());

        Venue venue = new Venue(
                dto.getName(),
                dto.getDescription(),
                dto.getCapacity(),
                city
        );

        return venueRepository.save(venue);
    }
}
