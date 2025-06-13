import React, { useEffect, useState } from "react";
import "../App.css";

const ParticipantList = () => {
    const [participants, setParticipants] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/participants")
            .then(response => response.json())
            .then(data => setParticipants(data))
            .catch(error => console.error("Błąd ładowania uczestników", error));
    }, []);

    return (
        <table className="data-table">
            <thead>
                <tr>
                    <th>Imię</th>
                    <th>Nazwisko</th>
                    <th>Obywatelstwo</th>
                    <th>Email</th>
                </tr>
            </thead>
            <tbody>
                {participants.map(participant => (
                    <tr key={participant.id}>
                        <td>{participant.firstName}</td>
                        <td>{participant.lastName}</td>
                        <td>{participant.country.name}</td>
                        <td>{participant.email}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default ParticipantList;