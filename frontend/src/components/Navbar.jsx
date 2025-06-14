import { Link } from "react-router-dom";
import "../App.css";

const Navbar = () => {
    return (
        <nav className="navbar">
            <ul className="nav-menu">
                <li className="dropdown">
                    <div className="dropdown-toggle">
                        Widoki
                    </div>
                    <ul className="dropdown-content">
                        <li><Link to="/events">Wydarzenia</Link></li>
                        <li><Link to="/organizers">Organizatorzy</Link></li>
                        <li><Link to="/participants">Uczestnicy</Link></li>
                        <li><Link to="/tickets">Bilety</Link></li>
                        <li><Link to="/venues">Miejsca</Link></li>
                    </ul>
                </li>

                <li className="dropdown">
                    <div className="dropdown-toggle">
                        Raporty
                    </div>
                    <ul className="dropdown-content">
                        <li><Link to="/reports/organizer-popularity">Popularność organizatorów</Link></li>
                        <li><Link to="/reports/country-participant">Uczestnicy wg kraju</Link></li>
                        <li><Link to="/reports/event-fill-rate">Wypełnienie wydarzeń</Link></li>
                    </ul>
                </li>

                <li className="dropdown">
                    <div className="dropdown-toggle">
                        Dodaj
                    </div>
                    <ul className="dropdown-content">
                        <li><Link to="/add-event">Dodaj wydarzenie</Link></li>
                        <li><Link to="/add-organizer">Dodaj organizatora</Link></li>
                        <li><Link to="/add-venue">Dodaj miejsce</Link></li>
                    </ul>
                </li>

                <li>
                    <div className="dropdown-toggle">
                        <Link to="/reserve">Rezerwuj bilet</Link>
                    </div>
                </li>
            </ul>
        </nav>
    );
};

export default Navbar;