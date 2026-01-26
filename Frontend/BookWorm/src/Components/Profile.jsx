import { useTheme } from "./ThemeContext";
import { useNavigate } from "react-router-dom";
import "./Profile.css";

export default function Profile({ isOpen, onClose }) {
  const { darkMode, toggleDarkMode } = useTheme();
  const navigate = useNavigate();

  const handleLogout = () => {
    // Here you would typically clear user session/token
    // For now, just navigate to login
    navigate("/");
    onClose();
  };

  if (!isOpen) return null;

  return (
    <>
      <div className="profile-overlay" onClick={onClose}></div>
      <div className="profile-dropdown">
        <div className="profile-header">
          <div className="profile-avatar">JD</div>
          <div className="profile-info">
            <div className="profile-name">John Doe</div>
            <div className="profile-email">john.doe@example.com</div>
          </div>
        </div>

        <div className="profile-menu">
          <div className="profile-item">
            <span>Theme</span>
            <button
              className="theme-toggle"
              onClick={toggleDarkMode}
            >
              {darkMode ? '☀️ Light' : '🌙 Dark'}
            </button>
          </div>

          <div className="profile-divider"></div>

          <button className="profile-item logout-btn" onClick={handleLogout}>
            <span>🚪 Logout</span>
          </button>
        </div>
      </div>
    </>
  );
}