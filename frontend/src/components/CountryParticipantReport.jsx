import React, { useEffect, useState } from "react";
import "../App.css";

const CountryParticipantReport = () => {
    const [data, setData] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/reports/participants/by-country")
            .then((res) => res.json())
            .then((data) => setData(data))
            .catch((err) => console.error("Błąd ładowania danych:", err));
    }, []);

    return (
        <div className="report-container">
            <h2>Uczestnicy według krajów</h2>
            <table>
                <thead>
                    <tr>
                        <th>Kraj</th>
                        <th>Liczba uczestników</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map((row, index) => (
                        <tr key={index}>
                            <td>{row.countryName}</td>
                            <td>{row.participantCount}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default CountryParticipantReport;