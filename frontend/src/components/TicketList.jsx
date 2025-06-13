import React, { useEffect, useState } from "react";
import "../App.css";

const TicketList = () => {
    const [tickets, setTickets] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/tickets")
            .then(response => response.json())
            .then(data => setTickets(data))
            .catch(error => console.error("Błąd ładowania biletów:", error));
    }, []);

    return (
        <table className="data-table">
            <thead>
                <tr>
                    <th>Wydarzenie</th>
                    <th>Imię</th>
                    <th>Nazwisko</th>
                    <th>Lokalizacja</th>
                    <th>Sektor</th>
                    <th>Rząd</th>
                    <th>Miejsce</th>
                    <th>Typ</th>
                    <th>Cena</th>
                </tr>
            </thead>
            <tbody>
                {tickets.map(ticket => (
                    <tr key={ticket.id}>
                        <td>{ticket.event.title}</td>
                        <td>{ticket.participant.firstName}</td>
                        <td>{ticket.participant.lastName}</td>
                        <td>{ticket.event.venue.name}</td>
                        <td>{ticket.seat.section}</td>
                        <td>{ticket.seat.row}</td>
                        <td>{ticket.seat.seatNumber}</td>
                        <td>{ticket.type}</td>
                        <td>{ticket.price}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default TicketList;