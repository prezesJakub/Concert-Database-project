package com.example.concertmanager.repository;

import com.example.concertmanager.dto.CountryParticipantReportDto;
import com.example.concertmanager.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("SELECT new com.example.concertmanager.dto.CountryParticipantReportDto(p.country.name, COUNT(p.id)) " +
            "FROM Participant p " +
            "GROUP BY p.country.name " +
            "ORDER BY COUNT(p.id) DESC")
    List<CountryParticipantReportDto> getCountryParticipantReport();
}
