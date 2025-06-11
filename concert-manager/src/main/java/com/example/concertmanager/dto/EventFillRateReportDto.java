package com.example.concertmanager.dto;

public class EventFillRateReportDto {
    private String eventName;
    private Long totalTickets;
    private int capacity;
    private double fillRate;

    public EventFillRateReportDto(String concertName, Long totalTickets, int capacity) {
        this.eventName = concertName;
        this.totalTickets = totalTickets;
        this.capacity = capacity;
        this.fillRate = capacity > 0 ? (double) totalTickets / capacity * 100 : 0;
    }
    public String getEventName() {
        return eventName;
    }
    public Long getTotalTickets() {
        return totalTickets;
    }
    public int getCapacity() {
        return capacity;
    }
    public double getFillRate() {
        return fillRate;
    }
}
