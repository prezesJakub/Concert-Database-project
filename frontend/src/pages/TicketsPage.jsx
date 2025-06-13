import React from "react";
import TicketList from "../components/TicketList";

const TicketsPage = () => {
    return (
        <div>
            <h1>Lista zarezerwowanych biletów</h1>
            <TicketList />
        </div>
    );
};

export default TicketsPage;