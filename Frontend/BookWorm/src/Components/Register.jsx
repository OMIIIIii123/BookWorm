import "./Register.css";

export default function Register() {
  return (
    <div className="register-page">
      <div className="register-card">

        {/* LEFT SECTION */}
        <div className="register-left">
          <div className="overlay">
            <h2>Unlock a world of digital stories.</h2>
            <p>
              Join the community of readers who never stop turning the page.
            </p>
          </div>
        </div>

        {/* RIGHT SECTION */}
        <div className="register-right">
          <p className="tag">Membership Application</p>
          <h1>Begin your story</h1>
          <p className="subtitle">
            Register to access our curated collection.
          </p>

          <form className="register-form">
            <input type="text" placeholder="Full Name" />
            <input type="email" placeholder="Email Address" />
            <input type="password" placeholder="Password" />

            <div className="terms">
              <input type="checkbox" />
              <span>
                I agree to the <b>Terms of Service</b> and Privacy Policy.
              </span>
            </div>

            <button className="primary-btn">
              Create Account →
            </button>

            <div className="social-buttons">
              <button>Google</button>
              <button>Apple</button>
            </div>

            <p className="login-text">
              Already have an account? <b>Log in</b>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
}
