import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./MyShelf.css";

export default function MyShelf() {
    const [activeTab, setActiveTab] = useState("all");

    const books = [
        {
            id: 1,
            title: "1984",
            author: "George Orwell",
            badge: "Permanent",
            progress: 100,
            cover:
                "https://images.unsplash.com/photo-1541963463532-d68292c34b19?q=80&w=400&auto=format&fit=crop",
            btn1: "Read Again",
            btn2: "Lend",
            disabled2: true,
        },
        {
            id: 2,
            title: "Dune",
            author: "Frank Herbert",
            badge: "Audiobook",
            progress: 0,
            cover:
                "https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?q=80&w=400&auto=format&fit=crop",
            btn1: "Listen",
            btn2: "",
        },
        {
            id: 3,
            title: "The Alchemist",
            author: "Paulo Coelho",
            badge: "Expires in 2h",
            progress: 89,
            cover:
                "https://images.unsplash.com/photo-1521587760476-6c12a4b040da?q=80&w=400&auto=format&fit=crop",
            btn1: "Finish",
            btn2: "Extend",
            disabled2: true,
        },
        {
            id: 4,
            title: "Sapiens: A Brief History",
            author: "Yuval Noah Harari",
            badge: "Lent",
            progress: 0,
            cover:
                "https://images.unsplash.com/photo-1521587760476-6c12a4b040da?q=80&w=400&auto=format&fit=crop",
            btn1: "Unavailable",
            btn2: "",
            disabled1: true,
        },
        {
            id: 5,
            title: "Thinking, Fast and Slow",
            author: "Daniel Kahneman",
            badge: "Audiobook",
            progress: 12,
            cover:
                "https://images.unsplash.com/photo-1519681393784-d120267933ba?q=80&w=400&auto=format&fit=crop",
            btn1: "Listen",
            btn2: "",
        },
        {
            id: 6,
            title: "The Great Gatsby",
            author: "F. Scott Fitzgerald",
            badge: "Permanent",
            progress: 45,
            cover:
                "https://images.unsplash.com/photo-1521587760476-6c12a4b040da?q=80&w=400&auto=format&fit=crop",
            btn1: "Read Now",
            btn2: "",
        },
    ];

    const filteredBooks =
        activeTab === "all"
            ? books
            : activeTab === "ebooks"
                ? books.filter((b) => b.badge !== "Audiobook")
                : books.filter((b) => b.badge === "Audiobook");

    return (
        <div className="shelf-page">
            {/* TOP NAV */}
            <div className="shelf-navbar">
                <div className="shelf-navbar-inner">
                    <div className="brand">
                        <div className="logo">📚</div>
                        Bookworm
                    </div>

                    <div className="nav-links">
                        <Link to="/home">Discover</Link>
                        <Link to="/library">Library</Link>
                        <Link to="/myshelf" className="active">
                            My Shelf
                        </Link>
                    </div>

                    <div className="nav-actions">
                        <button className="icon-btn">🔍</button>
                        <button className="icon-btn">🔔</button>
                        <button className="icon-btn">👤</button>
                    </div>
                </div>
            </div>

            {/* MAIN */}
            <div className="shelf-container">
                <h1 className="shelf-title">My Shelf</h1>
                <p className="shelf-subtitle">
                    Manage your reading progress and saved items.
                </p>

                {/* TABS */}
                <div className="tabs-row">
                    <button
                        className={`tab-btn ${activeTab === "all" ? "active" : ""}`}
                        onClick={() => setActiveTab("all")}
                    >
                        All Items <span className="tab-count">{books.length}</span>
                    </button>

                    <button
                        className={`tab-btn ${activeTab === "ebooks" ? "active" : ""}`}
                        onClick={() => setActiveTab("ebooks")}
                    >
                        eBooks{" "}
                        <span className="tab-count">
                            {books.filter((b) => b.badge !== "Audiobook").length}
                        </span>
                    </button>

                    <button
                        className={`tab-btn ${activeTab === "audio" ? "active" : ""}`}
                        onClick={() => setActiveTab("audio")}
                    >
                        Audiobooks{" "}
                        <span className="tab-count">
                            {books.filter((b) => b.badge === "Audiobook").length}
                        </span>
                    </button>
                </div>

                {/* GRID */}
                <div className="books-grid">
                    {filteredBooks.map((b) => (
                        <div className="book-card" key={b.id}>
                            <div className="book-top">
                                <div className="book-cover">
                                    <img src={b.cover} alt={b.title} />
                                </div>

                                <div className="book-info">
                                    <div className="book-title">{b.title}</div>
                                    <div className="book-author">{b.author}</div>
                                    <div className="badge">{b.badge}</div>
                                </div>
                            </div>

                            <div className="progress-wrap">
                                <div className="progress-row">
                                    <span>Progress</span>
                                    <span>{b.progress === 100 ? "Completed" : `${b.progress}%`}</span>
                                </div>

                                <div className="progress-bar">
                                    <div
                                        className="progress-fill"
                                        style={{ width: `${b.progress}%` }}
                                    />
                                </div>
                            </div>

                            <div className="card-actions">
                                <button
                                    className={`btn-primary ${b.disabled1 ? "btn-disabled" : ""}`}
                                    disabled={b.disabled1}
                                >
                                    {b.btn1}
                                </button>

                                {b.btn2 ? (
                                    <button
                                        className={`btn-outline ${b.disabled2 ? "btn-disabled" : ""}`}
                                        disabled={b.disabled2}
                                    >
                                        {b.btn2}
                                    </button>
                                ) : (
                                    <div />
                                )}
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            {/* FOOTER */}
            <div className="shelf-footer">
                <p>© {new Date().getFullYear()} Bookworm.com. All rights reserved.</p>
                <p>⚡ MyShelf</p>
            </div>
        </div>
    );
}
