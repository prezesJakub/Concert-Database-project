package com.example.concertmanager.dto;

public class OrganizerPopularityReportDto {
    private String organizerName;
    private Long ticketsSold;

    public OrganizerPopularityReportDto(String organizerName, long ticketsSold) {
        this.organizerName = organizerName;
        this.ticketsSold = ticketsSold;
    }
    public String getOrganizerName() {
        return organizerName;
    }
    public Long getTicketsSold() {
        return ticketsSold;
    }
}
