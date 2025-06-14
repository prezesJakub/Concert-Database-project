import React, { useEffect, useState } from "react";
import "../App.css";

const AddEventForm = () => {
    const [formData, setFormData] = useState({
        title: "",
        description: "",
        startDate: "",
        endDate: "",
        venueId: "",
        categoryId: "",
        organizerId: "",
        regularPrice: "",
        studentPrice: "",
        vipPrice: "",
    });

    const [venues, setVenues] = useState([]);
    const [categories, setCategories] = useState([]);
    const [organizers, setOrganizers] = useState([]);

    useEffect(() => {
        fetch("/api/venues")
            .then((res) => res.json())
            .then(setVenues)
            .catch((err) => console.error("Błąd ładowania miejsc:", err));

        fetch("/api/categories")
            .then((res) => res.json())
            .then(setCategories)
            .catch((err) => console.error("Błąd ładowania kategorii:", err));

        fetch("/api/organizers")
            .then((res) => res.json())
            .then(setOrganizers)
            .catch((err) => console.error("Błąd ładowania organizatorów:", err));
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const payload = {
            title: formData.title,
            description: formData.description,
            startDate: formData.startDate,
            endDate: formData.endDate,
            venue: { id: parseInt(formData.venueId) },
            category: { id: parseInt(formData.categoryId) },
            organizer: { id: parseInt(formData.organizerId) },
            regularPrice: parseFloat(formData.regularPrice),
            studentPrice: parseFloat(formData.studentPrice),
            vipPrice: parseFloat(formData.vipPrice),
        };

        try {
            const response = await fetch("/api/events", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });

            if(response.ok) {
                alert("Wydarzenie dodane");
                setFormData({
                    title: "",
                    description: "",
                    startDate: "",
                    endDate: "",
                    venueId: "",
                    categoryId: "",
                    organizerId: "",
                    regularPrice: "",
                    studentPrice: "",
                    vipPrice: "",
                });
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
            <h2>Dodaj nowe wydarzenie</h2>

            <div>
                <label>
                    Nazwa:
                    <input 
                        type="text"
                        name="title"
                        value={formData.title}
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
                        rows={4}
                    />
                </label>
            </div>

            <div>
                <label>
                    Data rozpoczęcia:
                    <input
                        type="datetime-local"
                        name="startDate"
                        value={formData.startDate}
                        onChange={handleInputChange}
                        required
                    />
                </label>
            </div>

            <div>
                <label>
                    Data zakończenia:
                    <input
                        type="datetime-local"
                        name="endDate"
                        value={formData.endDate}
                        onChange={handleInputChange}
                        required
                    />
                </label>
            </div>

            <div>
                <label>
                    Miejsce:
                    <select 
                        name="venueId"
                        value={formData.venueId}
                        onChange={handleInputChange}
                        required
                    >
                        <option value="">-- wybierz --</option>
                        {venues.map((venue) => (
                            <option key={venue.id} value={venue.id}>
                                {venue.name}
                            </option>
                        ))}
                    </select>
                </label>
            </div>

            <div>
                <label>
                    Kategoria:
                    <select
                        name="categoryId"
                        value={formData.categoryId}
                        onChange={handleInputChange}
                        required
                    >
                        <option value="">-- wybierz --</option>
                        {categories.map((category) => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </label>
            </div>

            <div>
                <label>
                    Organizator:
                    <select
                        name="organizerId"
                        value={formData.organizerId}
                        onChange={handleInputChange}
                        required
                    >
                        <option value="">-- wybierz --</option>
                        {organizers.map((org) => (
                            <option key={org.id} value={org.id}>
                                {org.name}
                            </option>
                        ))}
                    </select>
                </label>
            </div>

            <div>
                <label>
                    Cena REGULAR:
                    <input 
                        type="number"
                        name="regularPrice"
                        value={formData.regularPrice}
                        onChange={handleInputChange}
                        step="0.01"
                        min="0"
                        required
                    />
                </label>
            </div>

            <div>
                <label>
                    Cena STUDENT:
                    <input 
                        type="number"
                        name="studentPrice"
                        value={formData.studentPrice}
                        onChange={handleInputChange}
                        step="0.01"
                        min="0"
                        required
                    />
                </label>
            </div>

            <div>
                <label>
                    Cena VIP:
                    <input 
                        type="number"
                        name="vipPrice"
                        value={formData.vipPrice}
                        onChange={handleInputChange}
                        step="0.01"
                        min="0"
                        required
                    />
                </label>
            </div>

            <button type="submit" style={{ marginTop: "1rem" }}>
                Dodaj wydarzenie
            </button>
        </form>
    );
};

export default AddEventForm;