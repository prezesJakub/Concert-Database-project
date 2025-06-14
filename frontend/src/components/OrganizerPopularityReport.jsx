import React, { useEffect, useState } from "react";
import "../App.css";

const OrganizerPopularityReport = () => {
    const [data, setData] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/reports/organizers/popularity")
            .then((res) => res.json())
            .then((data) => setData(data))
            .catch((err) => console.error("Błąd ładowania danych:", err));
    }, []);

    return (
        <div className="report-container">
            <h2>Popularność organizatorów</h2>
            <table>
                <thead>
                    <tr>
                        <th>Organizator</th>
                        <th>Sprzedane bilety</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map((row, index) => (
                        <tr key={index}>
                            <td>{row.organizerName}</td>
                            <td>{row.ticketsSold}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default OrganizerPopularityReport;