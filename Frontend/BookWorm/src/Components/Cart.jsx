import "./Cart.css";

export default function Cart() {
  return (
    <div className="cart-page">
      <div className="cart-container">
        
        {/* LEFT: CART ITEMS */}
        <div className="cart-left">
          <h1>Your Shopping Cart</h1>
          <p className="cart-subtitle">
            You have 2 items in your cart ready for checkout.
          </p>

          {/* ITEM 1 */}
          <div className="cart-item">
            <img
              src="https://images-na.ssl-images-amazon.com/images/I/81F90H7hnML.jpg"
              alt="book"
            />
            <div className="item-info">
              <h3>The Midnight Library</h3>
              <p>by Matt Haig</p>
              <span className="badge">Fiction</span>
              <span className="badge">English</span>

              <div className="item-actions">
                <span className="remove">Remove</span>
                <span className="save">Save for Later</span>
              </div>
            </div>
            <div className="item-price">₹1,249</div>
          </div>

          {/* ITEM 2 */}
          <div className="cart-item">
            <img
              src="https://images-na.ssl-images-amazon.com/images/I/91uwocAMtSL.jpg"
              alt="book"
            />
            <div className="item-info">
              <h3>Becoming</h3>
              <p>by Michelle Obama</p>
              <span className="badge">Biography</span>
              <span className="badge">English</span>

              <div className="item-actions">
                <span className="remove">Remove</span>
                <span className="save">Save for Later</span>
              </div>
            </div>
            <div className="item-price">₹415</div>
          </div>
        </div>

        {/* RIGHT: SUMMARY */}
        <div className="cart-right">
          <h2>Order Summary</h2>

          <div className="summary-row">
            <span>Subtotal</span>
            <span>₹3,319</span>
          </div>

          <div className="summary-row discount">
            <span>Rental Discount</span>
            <span>- ₹1,660</span>
          </div>

          <div className="summary-total">
            <span>Total</span>
            <span>₹1,659</span>
          </div>

          <button className="primary-btn">
            Proceed to Checkout →
          </button>

          <p className="secure">Secure Checkout</p>
        </div>
      </div>
    </div>
  );
}
