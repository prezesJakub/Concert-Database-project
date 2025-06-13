package com.example.concertmanager.dto;

import com.example.concertmanager.entity.Seat;

public class SeatDto {
    private Long id;
    private String seatNumber;
    private String row;
    private String section;
    private Long venueId;

    public SeatDto(Seat seat) {
        this.id = seat.getId();
        this.seatNumber = seat.getSeatNumber();
        this.row = seat.getRow();
        this.section = seat.getSection();
        this.venueId = seat.getVenue() != null ? seat.getVenue().getId() : null;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
    public String getRow() {
        return row;
    }
    public void setRow(String row) {
        this.row = row;
    }
    public String getSection() {
        return section;
    }
    public void setSection(String section) {
        this.section = section;
    }
    public Long getVenueId() {
        return venueId;
    }
    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }
}
