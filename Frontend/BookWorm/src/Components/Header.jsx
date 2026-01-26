import "./Header.css";
import { Link, useLocation } from "react-router-dom";
import { useTheme } from "./ThemeContext";
import logo from "../assets/Images/logo.png";
import logoDark from "../assets/Images/logo_dark.png";
import Profile from "./Profile";
import { useState } from "react";

export default function Header() {
  const location = useLocation();
  const { darkMode } = useTheme();
  const [showProfile, setShowProfile] = useState(false);

  return (
    <header className="bw-header">

      {/* LEFT */}
      <div className="bw-header-left">
        <img src={darkMode ? logoDark : logo} alt="Bookworm Logo" className="bw-logo-img" />
      </div>

      {/* CENTER */}
      <nav className="bw-header-center">
        <Link to="/home" className={location.pathname === "/home" ? "active" : ""}>Home</Link>
        <Link to="/myshelf" className={location.pathname === "/myshelf" ? "active" : ""}>My Shelf</Link>
        <Link to="/mylibrary" className={location.pathname === "/mylibrary" ? "active" : ""}>Library</Link>
      </nav>

      {/* RIGHT */}
      <div className="bw-header-right">
        <Link to="/cart" className="bw-icon">🛒</Link>
        <div
          className="bw-avatar"
          onClick={() => setShowProfile(!showProfile)}
          style={{ cursor: 'pointer' }}
        >
          JD
        </div>
      </div>

      <Profile isOpen={showProfile} onClose={() => setShowProfile(false)} />

    </header>
  );
}
