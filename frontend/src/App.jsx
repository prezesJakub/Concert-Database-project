import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import React from "react";
import './App.css';
import OrganizersPage from './pages/OrganizersPage';

function App() {
  return (
    <div>
      <h1>Concert Manager</h1>
      <Router>
        <Routes>
          <Route path="/organizers" element={<OrganizersPage />} />
        </Routes>
      </Router>
    </div>
  )
}

export default App