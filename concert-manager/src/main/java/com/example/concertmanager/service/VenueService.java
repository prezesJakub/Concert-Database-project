package com.example.concertmanager.service;

import com.example.concertmanager.dto.VenueRequestDto;
import com.example.concertmanager.entity.City;
import com.example.concertmanager.entity.Seat;
import com.example.concertmanager.entity.Venue;
import com.example.concertmanager.repository.SeatRepository;
import com.example.concertmanager.repository.VenueRepository;
import org.springframework.stereotype.Service;

@Service
public class VenueService {

    private final VenueRepository venueRepository;
    private final LocationService locationService;
    private final SeatRepository seatRepository;

    public VenueService(VenueRepository venueRepository, LocationService locationService, SeatRepository seatRepository) {
        this.venueRepository = venueRepository;
        this.locationService = locationService;
        this.seatRepository = seatRepository;
    }

    public Venue createVenue(VenueRequestDto dto) {
        City city = locationService.findOrCreateCity(dto.getCityName(), dto.getCountryName());

        Venue venue = new Venue(
                dto.getName(),
                dto.getDescription(),
                0,
                city
        );

        venue = venueRepository.save(venue);

        int totalCapacity = 0;

        for(VenueRequestDto.SectorDto sector : dto.getSectors()) {
            for(int row = 1; row <= sector.getRowCount(); row++) {
                for(int seat = 1; seat <= sector.getSeatsPerRow(); seat++) {
                    String seatNumber = String.valueOf(totalCapacity+1);

                    Seat newSeat = new Seat(
                            seatNumber,
                            String.valueOf(row),
                            sector.getName(),
                            venue
                    );

                    seatRepository.save(newSeat);
                    totalCapacity++;
                }
            }
        }

        venue.setCapacity(totalCapacity);
        return venueRepository.save(venue);
    }
}
