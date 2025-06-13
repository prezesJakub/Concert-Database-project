import React, { useState } from "react";

const AddVenueForm = () => {
    const [formData, setFormData] = useState({
        name: "",
        description: "",
        cityName: "",
        countryName: "",
    });

    const [sectors, setSectors] = useState([]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSectorChange = (index, field, value) => {
        const updatedSectors = [...sectors];
        updatedSectors[index][field] = value;
        setSectors(updatedSectors);
    };

    const addSector = () => {
        setSectors((prev) => [
            ...prev,
            { sectionName: "", rowCount: "", seatsPerRow: "" },
        ]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const payload = {
            ...formData,
            sectors: sectors.map((sector) => ({
                name: sector.sectionName,
                rowCount: parseInt(sector.rowCount, 10),
                seatsPerRow: parseInt(sector.seatsPerRow, 10),
            })),
        };

        try {
            const response = await fetch("/api/venues", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(payload),
            });

            if(response.ok) {
                alert("Miejsce dodane");
                setFormData({
                    name: "",
                    description: "",
                    cityName: "",
                    countryName: "",
                });
                setSectors([]);
            } else {
                const errorText = await response.text();
                alert("Błąd: " + errorText);
            }
        } catch (error) {
            alert("Błąd połączenia: " + error.message);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="ticket-form-container">
            <h2>Dodaj nowe miejsce</h2>

            <div>
                <label>
                    Nazwa:
                    <input 
                        type="text"
                        name="name"
                        value={formData.name}
                        onChange={handleInputChange}
                        required
                    />
                </label>
            </div>
            
            <div>
                <label>
                    Opis:
                    <textarea
                        name="description"
                        value={formData.description}
                        onChange={handleInputChange}
                    />
                </label>
            </div>
            
            <div>
                <label>
                    Miasto:
                    <input 
                        type="text"
                        name="cityName"
                        value={formData.cityName}
                        onChange={handleInputChange}
                        required
                    />
                </label>
            </div>
            
            <div>
                <label>
                    Kraj:
                    <input 
                        type="text"
                        name="countryName"
                        value={formData.countryName}
                        onChange={handleInputChange}
                        required
                    />
                </label>
            </div> 

            <h3>Sektory</h3>
            {sectors.map((sector, index) => (
                <div
                    key={index}
                    style={{ border: "1px solid #ccc", padding: "1rem", marginBottom: "1rem" }}
                >
                    <label>
                        Nazwa sektora:
                        <input 
                            type="text"
                            value={sector.sectionName}
                            onChange={(e) => handleSectorChange(index, "sectionName", e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        Liczba rzędów:
                        <input 
                            type="number"
                            value={sector.rowCount}
                            onChange={(e) => handleSectorChange(index, "rowCount", e.target.value)}
                            required
                            min="1"
                        />
                    </label>
                    <label>
                        Miejsc w rzędzie:
                        <input 
                            type="number"
                            value={sector.seatsPerRow}
                            onChange={(e) => handleSectorChange(index, "seatsPerRow", e.target.value)}
                            required
                            min="1"
                        />
                    </label>
                </div>
            ))}

            <button type="button" onClick={addSector}>
                Dodaj sektor
            </button>

            <br />
            <button type="submit" style={{ marginTop: "1rem" }}>
                Dodaj miejsce
            </button>
        </form>
    );
};

export default AddVenueForm;