import React, { useEffect, useState } from "react";
import "../App.css";

const VenueList = () => {
    const [venues, setVenues] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/venues")
            .then(response => response.json())
            .then(data => setVenues(data))
            .catch(error => console.error("Błąd ładowania miejsc:", error));
    }, []);

    return (
        <table className="data-table">
            <thead>
                <tr>
                    <th>Nazwa</th>
                    <th>Miasto</th>
                    <th>Kraj</th>
                    <th>Pojemność</th>
                    <th>Opis</th>
                </tr>
            </thead>
            <tbody>
                {venues.map(venue => (
                    <tr key={venue.id}>
                        <td>{venue.name}</td>
                        <td>{venue.city.name}</td>
                        <td>{venue.city.country.name}</td>
                        <td>{venue.capacity}</td>
                        <td>{venue.description}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default VenueList;