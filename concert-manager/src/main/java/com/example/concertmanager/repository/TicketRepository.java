package com.example.concertmanager.repository;

import com.example.concertmanager.dto.OrganizerPopularityReportDto;
import com.example.concertmanager.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsByEventIdAndSeatId(Long eventId, Long seatId);

    @Query("SELECT new com.example.concertmanager.dto.OrganizerPopularityReportDto(o.name, COUNT(t.id)) " +
            "FROM Ticket t " +
            "JOIN t.event e " +
            "JOIN e.organizer o " +
            "GROUP BY o.name " +
            "ORDER BY COUNT(t.id) DESC")
    List<OrganizerPopularityReportDto> getOrganizerPopularityReport();
}
