import React, { useEffect, useState } from "react";
import "../App.css";

const TicketReservationForm = () => {
    const [participants, setParticipants] = useState([]);
    const [events, setEvents] = useState([]);
    const [seats, setSeats] = useState([]);
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
            .then(data => Array.isArray(data) ? setSeats(data) : setSeats([]))
            .catch(err => {
                console.error("Błąd podczas pobierania miejsc:", err);
                setSeats([]);
            });
    }, []);

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
                        {seats.map(s => (
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
                    <input 
                        type="number"
                        name="price"
                        value={formData.price}
                        onChange={handleChange}
                        required
                        step="0.01"
                        min="0"
                    />
                </div>

                <button type="submit">Rezerwuj</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default TicketReservationForm;