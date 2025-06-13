import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import React from "react";
import './App.css';
import EventsPage from "./pages/EventsPage";
import OrganizersPage from './pages/OrganizersPage';
import ParticipantsPage from './pages/ParticipantsPage';
import VenuesPage from './pages/VenuesPage';
import TicketsPage from './pages/TicketsPage';
import ReserveTicketPage from './pages/ReserveTicketPage';
import AddVenuePage from './pages/AddVenuePage';

function App() {
  return (
    <div>
      <h1>Concert Manager</h1>
      <Router>
        <Routes>
          <Route path="/events" element={<EventsPage />} />
          <Route path="/organizers" element={<OrganizersPage />} />
          <Route path="/participants" element={<ParticipantsPage />} />
          <Route path="/venues" element={<VenuesPage />} />
          <Route path="/tickets" element={<TicketsPage />} />
          <Route path="/reserve" element={<ReserveTicketPage />} />
          <Route path="/add-venue" element={<AddVenuePage />} />
        </Routes>
      </Router>
    </div>
  )
}

export default App;