import { useState } from "react";
import "./SearchBar.css";

export default function SearchBar() {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [searchType, setSearchType] = useState("name");
  const [searchQuery, setSearchQuery] = useState("");

  const searchOptions = [
    { value: "name", label: "Search by name", placeholder: "Search by book title..." },
    { value: "author", label: "Search by author", placeholder: "Search by author name..." }
  ];

  const handleInputClick = () => {
    setIsDropdownOpen(true);
  };

  const handleInputFocus = () => {
    setIsDropdownOpen(true);
  };

  const handleInputBlur = () => {
    // Delay closing to allow for option selection
    setTimeout(() => setIsDropdownOpen(false), 150);
  };

  const handleOptionSelect = (option) => {
    setSearchType(option.value);
    setSearchQuery("");
    setIsDropdownOpen(false);
  };

  const handleSearch = () => {
    if (searchQuery.trim()) {
      console.log(`Searching for "${searchQuery}" by ${searchType}`);
      // Here you would implement the actual search logic
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
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
          disabled={!searchQuery.trim()}
        >
          Search
        </button>
      </div>
    </div>
  );
}