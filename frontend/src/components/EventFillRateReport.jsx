import React, { useEffect, useState } from "react";
import "../App.css";

const EventFillRateReport = () => {
    const [data, setData] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/reports/events/fill-rate")
            .then((res) => res.json())
            .then((data) => setData(data))
            .catch((err) => console.error("Błąd ładowania danych:", err));
    }, []);

    return (
        <div className="report-container">
            <h2>Wypełnienie wydarzeń</h2>
            <table>
                <thead>
                    <tr>
                        <th>Wydarzenie</th>
                        <th>Sprzedane bilety</th>
                        <th>Pojemność</th>
                        <th>Wypełnienie (%)</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map((row, index) => (
                        <tr key={index}>
                            <td>{row.eventName}</td>
                            <td>{row.totalTickets}</td>
                            <td>{row.capacity}</td>
                            <td>{row.fillRate.toFixed(2)}%</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default EventFillRateReport;