package com.example.concertmanager.service;

import com.example.concertmanager.dto.CountryParticipantReportDto;
import com.example.concertmanager.dto.EventFillRateReportDto;
import com.example.concertmanager.dto.OrganizerPopularityReportDto;
import com.example.concertmanager.repository.ParticipantRepository;
import com.example.concertmanager.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final TicketRepository ticketRepository;
    private final ParticipantRepository participantRepository;

    public ReportService(TicketRepository ticketRepository, ParticipantRepository participantRepository) {
        this.ticketRepository = ticketRepository;
        this.participantRepository = participantRepository;
    }

    public List<OrganizerPopularityReportDto> getOrganizerPopularity() {
        return ticketRepository.getOrganizerPopularityReport();
    }

    public List<CountryParticipantReportDto> getCountryParticipants() {
        return participantRepository.getCountryParticipantReport();
    }

    public List<EventFillRateReportDto> getEventFillRates() {
        return ticketRepository.getEventFillRateReport();
    }
}
