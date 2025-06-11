package com.example.concertmanager.controller;

import com.example.concertmanager.dto.CountryParticipantReportDto;
import com.example.concertmanager.dto.EventFillRateReportDto;
import com.example.concertmanager.dto.OrganizerPopularityReportDto;
import com.example.concertmanager.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/organizers/popularity")
    public List<OrganizerPopularityReportDto> getOrganizerPopularity() {
        return reportService.getOrganizerPopularity();
    }

    @GetMapping("/participants/by-country")
    public List<CountryParticipantReportDto> getParticipantsByCountry() {
        return reportService.getCountryParticipants();
    }

    @GetMapping("/events/fill-rate")
    public List<EventFillRateReportDto> getEventFillRates() {
        return reportService.getEventFillRates();
    }
}
