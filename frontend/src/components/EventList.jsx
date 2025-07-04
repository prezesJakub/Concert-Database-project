import { useEffect, useState } from "react";
import "../App.css";

export default function EventList() {
    const [events, setEvents] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/events')
            .then(response => response.json())
            .then(data => setEvents(data))
            .catch(error => console.error('Błąd ładowania:', error));
    }, []);

    return (
        <table className="data-table">
            <thead>
                <tr>
                    <th>Nazwa</th>
                    <th>Organizator</th>
                    <th>Data</th>
                    <th>Miejsce</th>
                    <th>Kategoria</th>  
                    <th>Opis</th>
                    <th>Cena REGULAR</th>
                    <th>Cena STUDENT</th>
                    <th>Cena VIP</th>
                </tr>
            </thead>
            <tbody>
                {events.map(event => (
                    <tr key={event.id}>
                        <td>{event.title}</td>
                        <td>{event.organizer.name}</td>
                        <td>{new Date(event.startDate).toLocaleString('pl-PL', {
                            day: 'numeric',
                            month: 'long',
                            year: 'numeric',
                            hour: '2-digit',
                            minute: '2-digit'
                        })}</td>
                        <td>{event.venue?.name}</td>
                        <td>{event.category.name}</td>
                        <td>{event.description}</td>
                        <td>{event.regularPrice}</td>
                        <td>{event.studentPrice}</td>
                        <td>{event.vipPrice}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
}