package com.example.concertmanager.dto;

public class CountryParticipantReportDto {
    private String countryName;
    private Long participantCount;

    public CountryParticipantReportDto(String countryName, Long participantCount) {
        this.countryName = countryName;
        this.participantCount = participantCount;
    }

    public String getCountryName() {
        return countryName;
    }
    public Long getParticipantCount() {
        return participantCount;
    }
}
