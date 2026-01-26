import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Login.css";

export default function Login() {
    const navigate = useNavigate();

    const [form, setForm] = useState({
        email: "",
        password: "",
        remember: false,
    });

    const [showPassword, setShowPassword] = useState(false);

    const onChange = (e) => {
        const { name, value, type, checked } = e.target;
        setForm((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Login payload:", form);
        navigate("/home");
    };

    return (
        <div className="auth-page">
            <div className="auth-shell">
                {/* LEFT IMAGE */}
                <div className="auth-left">
                    <div className="auth-left-overlay" />

                    <div className="auth-left-content">
                        <div className="auth-brand">
                            <div className="auth-brand-icon">📚</div>
                            <span className="auth-brand-name">Bookworm</span>
                        </div>

                        <h1 className="auth-left-title">Unlock a world of digital stories.</h1>
                        <p className="auth-left-subtitle">
                            Join the community of readers who never stop turning the page.
                        </p>
                    </div>
                </div>

                {/* RIGHT FORM */}
                <div className="auth-right">
                    <div className="auth-header">
                        <div>
                            <p className="auth-tag">MEMBER LOGIN</p>
                            <h2 className="auth-title">Welcome back</h2>
                            <p className="auth-desc">Login to access your curated collection.</p>
                        </div>

                    </div>

                    <form className="auth-form" onSubmit={handleSubmit}>
                        {/* Email */}
                        <div className="form-group">
                            <label className="form-label">Email Address</label>
                            <input
                                className="form-input"
                                type="email"
                                name="email"
                                value={form.email}
                                onChange={onChange}
                                placeholder="you@example.com"
                                required
                            />
                        </div>

                        {/* Password */}
                        <div className="form-group">
                            <div className="password-row">
                                <label className="form-label">Password</label>
                                <Link className="forgot-link" to="/forgot-password">
                                    Forgot password?
                                </Link>
                            </div>

                            <div className="password-wrap">
                                <input
                                    className="form-input"
                                    type={showPassword ? "text" : "password"}
                                    name="password"
                                    value={form.password}
                                    onChange={onChange}
                                    placeholder="Enter your password"
                                    required
                                />

                                <button
                                    type="button"
                                    className="show-btn"
                                    onClick={() => setShowPassword((s) => !s)}
                                >
                                    {showPassword ? "Hide" : "Show"}
                                </button>
                            </div>

                            <p className="hint-text">Must be at least 8 characters.</p>
                        </div>

                        {/* Remember */}
                        <div className="remember-row">
                            <label className="remember-label">
                                <input
                                    type="checkbox"
                                    name="remember"
                                    checked={form.remember}
                                    onChange={onChange}
                                />
                                Remember me
                            </label>
                        </div>

                        {/* Login */}
                        <button className="primary-btn" type="submit">
                            Login →
                        </button>

                        {/* Divider */}
                        <div className="divider">
                            <span />
                            <p>OR</p>
                            <span />
                        </div>

                        {/* Social */}
                        <div className="social-row">
                            <button type="button" className="social-btn">
                                <img
                                    className="social-icon"
                                    src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/google/google-original.svg"
                                    alt="Google"
                                />
                                <span>Google</span>
                            </button>

                            <button type="button" className="social-btn">
                                <img
                                    className="social-icon"
                                    src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/facebook/facebook-original.svg"
                                    alt="Facebook"
                                />
                                <span>Facebook</span>
                            </button>
                        </div>

                        {/* Bottom */}
                        <p className="bottom-text">
                            Don’t have an account?{" "}
                            <Link className="bottom-link" to="/register">
                                Register for free
                            </Link>
                        </p>

                        <p className="footer-text">
                            © {new Date().getFullYear()} Bookworm.com. All rights reserved.
                        </p>
                    </form>
                </div>
            </div>
        </div>
    );
}
