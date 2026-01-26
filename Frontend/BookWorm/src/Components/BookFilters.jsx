import { useState } from "react";
import "./BookFilters.css";

export default function BookFilters() {
  const [openDropdown, setOpenDropdown] = useState(null); // null, 'genre', or 'language'

  const genres = [
    "Literary Fiction",
    "Fantasy",
    "Science Fiction",
    "Mystery",
    "Thriller & Suspense",
    "Historical Fiction",
    "Romance",
    "Horror",
    "Dystopian",
    "Contemporary Fiction",
    "Biography & Autobiography",
    "Memoir",
    "Self-Help",
    "True Crime",
    "History"
  ];

  const languages = [
    "English",
    "Marathi",
    "Hindi",
    "Spanish"
  ];

  return (
    <aside className="book-filters">
      <h3>Filters</h3>

      {/* FORMAT */}
      <div className="filter-section">
        <h4>Format</h4>
        <div className="filter-options">
          <label className="filter-option">
            <input type="checkbox" />
            <span>eBooks</span>
          </label>
          <label className="filter-option">
            <input type="checkbox" />
            <span>Audiobooks</span>
          </label>
        </div>
      </div>

      {/* GENRE DROPDOWN */}
      <div className="filter-section">
        <h4>Genre</h4>
        <div className="dropdown-container">
          <button
            className="dropdown-toggle"
            onClick={() => setOpenDropdown(openDropdown === 'genre' ? null : 'genre')}
          >
            Select Genres
            <span className={`dropdown-arrow ${openDropdown === 'genre' ? 'open' : ''}`}>▼</span>
          </button>
          {openDropdown === 'genre' && (
            <div className="dropdown-menu">
              {genres.map((genre, index) => (
                <label key={index} className="dropdown-option">
                  <input type="checkbox" />
                  <span>{genre}</span>
                </label>
              ))}
            </div>
          )}
        </div>
      </div>

      {/* LANGUAGE DROPDOWN */}
      <div className="filter-section">
        <h4>Language</h4>
        <div className="dropdown-container">
          <button
            className="dropdown-toggle"
            onClick={() => setOpenDropdown(openDropdown === 'language' ? null : 'language')}
          >
            Select Languages
            <span className={`dropdown-arrow ${openDropdown === 'language' ? 'open' : ''}`}>▼</span>
          </button>
          {openDropdown === 'language' && (
            <div className="dropdown-menu">
              {languages.map((language, index) => (
                <label key={index} className="dropdown-option">
                  <input type="checkbox" />
                  <span>{language}</span>
                </label>
              ))}
            </div>
          )}
        </div>
      </div>

      <button className="reset-filters-btn">Reset Filters</button>
    </aside>
  );
}