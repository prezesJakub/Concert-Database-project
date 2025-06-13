import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import React from "react";
import './App.css';
import EventsPage from "./pages/EventsPage";
import OrganizersPage from './pages/OrganizersPage';
import ParticipantsPage from './pages/ParticipantsPage';

function App() {
  return (
    <div>
      <h1>Concert Manager</h1>
      <Router>
        <Routes>
          <Route path="/events" element={<EventsPage />} />
          <Route path="/organizers" element={<OrganizersPage />} />
          <Route path="/participants" element={<ParticipantsPage />} />
        </Routes>
      </Router>
    </div>
  )
}

export default App;