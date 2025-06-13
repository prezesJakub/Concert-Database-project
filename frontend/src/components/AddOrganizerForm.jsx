import React, { useState } from "react";
import "../App.css";

const AddOrganizerForm = () => {
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        phone: ""
    });
    const [message, setMessage] = useState("");

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setMessage("");

        fetch("http://localhost:8080/api/organizers", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })
        .then(async (response) => {
            if(response.ok) {
                setMessage("Organizator został dodany!");
                setFormData({ name: "", email: "", phone: "" });
            } else {
                const errorText = await response.text();
                setMessage(`Błąd: ${errorText}`);
            }
        })
        .catch((err) => setMessage(`Błąd połączenia: ${err.message}`));
    };

    return (
        <div className="ticket-form-container">
            <h2>Dodaj nowego organizatora</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nazwa organizatora:</label>
                    <input 
                        type="text"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Email:</label>
                    <input 
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Telefon:</label>
                    <input 
                        type="text"
                        name="phone"
                        value={formData.phone}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Dodaj organizatora</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default AddOrganizerForm;