import "./Home.css";
import BookFilters from "./BookFilters";
import SearchBar from "./SearchBar";

export default function Home() {
  return (
    <div className="home-page">

      {/* HERO */}
      <section className="home-hero">
        <h1>Discover Your Next Adventure</h1>

        <SearchBar />

        <div className="home-trending">
          <span>Trending:</span>
          <span className="chip">#SciFi</span>
          <span className="chip">#Audiobooks</span>
          <span className="chip">#BestSellers</span>
        </div>
      </section>

      {/* CONTENT */}
      <div className="home-content">

        {/* FILTERS */}
        <BookFilters />

        {/* GRID */}
        <section className="book-grid">
          {[1,2,3,4,5,6].map(i => (
            <div className="book-card" key={i}>
              <div className="book-img"></div>
              <h4>The Silent Echo</h4>
              <p>by Elena Ferrante</p>
              <div className="price-section">
                <div className="original-price">₹1,299</div>
                <div className="discounted-price">₹899</div>
              </div>
              <div className="button-group">
                <button className="add-to-cart-btn">Add to Cart</button>
                <button className="buy-now-btn">Buy Now</button>
              </div>
            </div>
          ))}
        </section>
      </div>

    </div>
  );
}
