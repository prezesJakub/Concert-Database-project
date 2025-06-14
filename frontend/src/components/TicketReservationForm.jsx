import React, { useEffect, useState } from "react";
import "../App.css";

const TicketReservationForm = () => {
    const [participants, setParticipants] = useState([]);
    const [events, setEvents] = useState([]);
    const [allSeats, setAllSeats] = useState([]);
    const [filteredSeats, setFilteredSeats] = useState([]);
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        countryName: "",
        eventId: "",
        seatId: "",
        ticketType: "REGULAR",
        price: ""
    });
    const [message, setMessage] = useState("");

    useEffect(() => {
        fetch("http://localhost:8080/api/participants")
            .then(res => res.json())
            .then(data => setParticipants(data));

        fetch("http://localhost:8080/api/events")
            .then(res => res.json())
            .then(data => setEvents(data));

        fetch("http://localhost:8080/api/seats")
            .then(res => res.json())
            .then(data => Array.isArray(data) ? setAllSeats(data) : setAllSeats([]))
            .catch(err => {
                console.error("Błąd podczas pobierania miejsc:", err);
                setAllSeats([]);
            });
    }, []);

    useEffect(() => {
        if(!formData.eventId) {
            setFilteredSeats([]);
            return;
        }

        const selectedEvent = events.find(e => e.id.toString() === formData.eventId);
        if(!selectedEvent || !selectedEvent.venue || !selectedEvent.venue.id) {
            setFilteredSeats([]);
            return;
        }

        const venueId = selectedEvent.venue.id;
        const matchingSeats = allSeats.filter(seat =>
            seat.venueId === venueId
        );
        setFilteredSeats(matchingSeats);
    }, [formData.eventId, events, allSeats]);

    useEffect(() => {
        const selectedEvent = events.find(e => e.id.toString() === formData.eventId);
        if (!selectedEvent) return;

        let selectedPrice;
        switch(formData.ticketType) {
            case "REGULAR":
                selectedPrice = selectedEvent.regularPrice;
                break;
            case "STUDENT":
                selectedPrice = selectedEvent.studentPrice;
                break;
            case "VIP":
                selectedPrice = selectedEvent.vipPrice;
                break;
            default:
                selectedPrice = "";
        }

        setFormData(prev => ({
            ...prev,
            price: selectedPrice ?? ""
        }));
    }, [formData.eventId, formData.ticketType, events]);

    const handleChange = e => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = e => {
        e.preventDefault();
        setMessage("");

        fetch(`http://localhost:8080/api/tickets/reserve`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })
        .then(async response => {
            if (response.ok) {
                setMessage("Bilet został zarezerwowany!");
                setFormData({
                    firstName: "",
                    lastName: "",
                    email: "",
                    countryName: "",
                    eventId: "",
                    seatId: "",
                    ticketType: "REGULAR",
                    price: ""
                });
                setFilteredSeats([]);
            } else {
                const errorText = await response.text();
                setMessage(`Błąd: ${errorText}`);
            }
        })
        .catch(err => setMessage(`Błąd połączenia: ${err.message}`));
    };

    return (
        <div className="ticket-form-container">
            <h2>Zarezerwuj bilet</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <div>
                        <label>Imię:</label>
                        <input type="text" name="firstName" value={formData.firstName} onChange={handleChange} required />
                    </div>
                    <div>
                        <label>Nazwisko:</label>
                        <input type="text" name="lastName" value={formData.lastName} onChange={handleChange} required />
                    </div>
                    <div>
                        <label>Email:</label>
                        <input type="text" name="email" value={formData.email} onChange={handleChange} required />
                    </div>
                    <div>
                        <label>Kraj:</label>
                        <input type="text" name="countryName" value={formData.countryName} onChange={handleChange} required />
                    </div>
                </div>

                <div>
                    <label>Wydarzenie:</label>
                    <select name="eventId" value={formData.eventId} onChange={handleChange} required>
                        <option value="">-- wybierz --</option>
                        {events.map(e => (
                            <option key={e.id} value={e.id}>{e.title}</option>
                        ))}
                    </select>
                </div>

                <div>
                    <label>Miejsce:</label>
                    <select name="seatId" value={formData.seatId} onChange={handleChange} required>
                        <option value="">-- wybierz --</option>
                        {filteredSeats.map(s => (
                            <option key={s.id} value={s.id}>
                                Sektor {s.section}, Rząd {s.row}, Miejsce {s.seatNumber} 
                            </option>
                        ))}
                    </select>
                </div>

                <div>
                    <label>Typ biletu:</label>
                    <select name="ticketType" value={formData.ticketType} onChange={handleChange}>
                        <option value="REGULAR">Regular</option>
                        <option value="VIP">VIP</option>
                        <option value="STUDENT">Student</option>
                    </select>
                </div>

                <div>
                    <label>Cena:</label>
                    <div className="price-display">{formData.price ? `${formData.price.toFixed(2)} zł` : "--"}</div>
                </div>

                <button type="submit">Rezerwuj</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default TicketReservationForm;