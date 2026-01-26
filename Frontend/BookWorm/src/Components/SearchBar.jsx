import { useState } from "react";
import "./SearchBar.css";

export default function SearchBar() {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [searchType, setSearchType] = useState("name");
  const [searchQuery, setSearchQuery] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [showResults, setShowResults] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const searchOptions = [
    { value: "name", label: "Search by Name", placeholder: "Search by book title..." },
    { value: "author", label: "Search by Author", placeholder: "Search by author name..." }
  ];

  const handleInputClick = () => {
    setIsDropdownOpen(true);
    setShowResults(false);
  };

  const handleInputFocus = () => {
    setIsDropdownOpen(true);
    setShowResults(false);
  };

  const handleInputBlur = () => {
    // Delay closing to allow for option selection
    setTimeout(() => {
      setIsDropdownOpen(false);
      // Don't auto-close results on blur immediately to allow interaction, 
      // or handle it with a click outside listener (more complex). 
      // For now we'll close only if clicking away effectively.
      // But standard behavior: blur closes results.
      setShowResults(false); 
    }, 200);
  };

  const handleOptionSelect = (option) => {
    setSearchType(option.value);
    setSearchQuery("");
    setIsDropdownOpen(false);
  };

  const handleSearch = async () => {
    if (searchQuery.trim()) {
      setIsLoading(true);
      setShowResults(true);
      setIsDropdownOpen(false);

      try {
        let url = `http://localhost:9090/api/books/search?query=${encodeURIComponent(searchQuery)}`;
        if (searchType === 'author') {
          url = `http://localhost:9090/api/books/search/author?query=${encodeURIComponent(searchQuery)}`;
        }
        
        const response = await fetch(url);
        if (response.ok) {
          const data = await response.json();
          setSearchResults(data);
        } else {
          setSearchResults([]);
        }
      } catch (error) {
        console.error("Error fetching search results:", error);
        setSearchResults([]);
      } finally {
        setIsLoading(false);
      }
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };

  // Prevent blur when clicking on results
  const handleMouseDownResult = (e) => {
    e.preventDefault(); 
  };

  const currentOption = searchOptions.find(option => option.value === searchType);

  return (
    <div className="search-bar-container">
      <div className="search-bar">
        <div className="search-input-wrapper">
          <input
            type="text"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            onClick={handleInputClick}
            onFocus={handleInputFocus}
            onBlur={handleInputBlur}
            onKeyPress={handleKeyPress}
            placeholder={currentOption.placeholder}
            className="search-input"
          />
          {isDropdownOpen && (
            <div className="search-dropdown">
            {searchOptions.map((option) => (
                <div
                  key={option.value}
                  className={`search-option ${searchType === option.value ? 'active' : ''}`}
                  onClick={() => handleOptionSelect(option)}
                >
                  {option.label}
                </div>
              ))}
            </div>
          )}
        </div>
        <button
          className="search-button"
          onClick={handleSearch}
          disabled={!searchQuery.trim() || isLoading}
        >
          {isLoading ? "..." : "Search"}
        </button>

        {showResults && (
        <div className="search-results-dropdown" onMouseDown={handleMouseDownResult}>
          {isLoading ? (
            <div className="search-no-results">Searching...</div>
          ) : searchResults.length > 0 ? (
            searchResults.map((book) => (
              <div key={book.id} className="search-result-item">
                <div className="search-result-info">
                  <span className="search-result-title">{book.title}</span>
                  <span className="search-result-subtitle">{book.englishTitle || book.author || "Unknown Author"}</span>
                </div>
                <div className="search-result-price">
                  ₹{book.offerPrice || book.basePrice}
                </div>
              </div>
            ))
          ) : (
            <div className="search-no-results">No books found</div>
          )}
        </div>
        )}
      </div>
    </div>
  );
}