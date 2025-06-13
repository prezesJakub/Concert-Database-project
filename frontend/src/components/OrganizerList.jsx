import React, { useEffect, useState } from "react";
import "../App.css";

const OrganizerList = () => {
    const [organizers, setOrganizers] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/organizers')
            .then(response => response.json())
            .then(data => setOrganizers(data))
            .catch(error => console.error('Błąd ładowania organizatorów:', error));
    }, []);

    return (
        <table className="data-table">
            <thead>
                <tr>
                    <th>Nazwa</th>
                    <th>Email</th>
                    <th>Telefon</th>
                </tr>
            </thead>
            <tbody>
                {organizers.map((organizer) => (
                    <tr key={organizer.id}>
                        <td>{organizer.name}</td>
                        <td>{organizer.email}</td>
                        <td>{organizer.phone}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default OrganizerList;