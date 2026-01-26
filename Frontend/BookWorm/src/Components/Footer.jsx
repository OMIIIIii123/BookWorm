import "./Footer.css";

export default function Footer() {
  return (
    <footer className="bw-footer">
      <div className="bw-footer-col">
        <h4>Bookworm</h4>
        <p>Your gateway to endless stories.</p>
      </div>

      <div className="bw-footer-col">
        <h4>Discover</h4>
        <p>Bestsellers</p>
        <p>New Releases</p>
        <p>Genres</p>
      </div>

      <div className="bw-footer-col">
        <h4>Help</h4>
        <p>Support Center</p>
        <p>Return Policy</p>
        <p>Contact Us</p>
      </div>

      <div className="bw-footer-col">
        <h4>Newsletter</h4>
        <p>Get special offers and updates.</p>
        <div className="bw-newsletter">
          <input placeholder="Email" />
          <button>→</button>
        </div>
      </div>
    </footer>
  );
}
