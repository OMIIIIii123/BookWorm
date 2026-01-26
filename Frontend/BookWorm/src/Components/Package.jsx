import { useState, useEffect } from "react";
import "./Package.css";

export default function Package({ authorName = "Rowling" }) {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Determine the user's "subscribed" author.
    // In a real app, this would come from user context/profile.
    // For now, we accept it as a prop or default to "Rowling".
    
    const fetchPackageBooks = async () => {
      try {
        setLoading(true);
        // Use the newly created author search endpoint
        const response = await fetch(
          `http://localhost:9090/api/books/search/author?query=${encodeURIComponent(authorName)}&limit=10`
        );
        if (response.ok) {
          const data = await response.json();
          setBooks(data);
        }
      } catch (error) {
        console.error("Failed to fetch subscribed package books", error);
      } finally {
        setLoading(false);
      }
    };

    fetchPackageBooks();
  }, [authorName]);

  if (!books.length && !loading) return null; // Don't show if no books

  return (
    <div className="package-carousel-container">
      <div className="package-header">
        <div>
          <h2 className="package-title">Your Author Subscription</h2>
          <span className="package-subtitle">Curated picks from {authorName}</span>
        </div>
      </div>

      {loading ? (
        <div className="package-loading">Loading your subscription...</div>
      ) : (
        <div className="carousel-viewport">
          {books.map((book) => (
            <div className="package-card" key={book.id}>
              <div className="card-image">
                {/* Placeholder for cover image since we don't have real URLs yet */}
                <div className="card-image-placeholder">
                  {book.title.substring(0, 2).toUpperCase()}
                </div>
              </div>
              <div className="card-content">
                <div className="card-title" title={book.title}>{book.title}</div>
                <div className="card-author">By {authorName}</div>
                
                <div className="card-price">
                   {book.offerPrice ? `₹${book.offerPrice}` : `₹${book.basePrice}`}
                </div>

                <div className="card-actions">
                  <button className="btn-action btn-rent" onClick={() => console.log('Rent', book.id)}>
                    Rent
                  </button>
                  <button className="btn-action btn-lend" onClick={() => console.log('Lend', book.id)}>
                    Lend
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
